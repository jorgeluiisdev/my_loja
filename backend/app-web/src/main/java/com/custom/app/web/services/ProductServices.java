package com.custom.app.web.services;

import com.custom.app.core.model.Category;
import com.custom.app.core.model.Image;
import com.custom.app.core.model.ImageType;
import com.custom.app.core.model.Product;
import com.custom.app.core.usecase.category.CreateCategoryUseCase;
import com.custom.app.core.usecase.category.GetCategoryUseCase;
import com.custom.app.core.usecase.image.ListImageUseCase;
import com.custom.app.core.usecase.product.CreateProductUseCase;
import com.custom.app.core.usecase.product.GetProductUseCase;
import com.custom.app.core.usecase.product.ListProductUseCase;
import com.custom.app.core.usecase.product.UpdateProductUseCase;
import com.custom.app.web.dto.request.CreateProductRequest;
import com.custom.app.web.dto.request.UpdateProductRequest;
import com.custom.app.web.dto.response.CategoryProductsResponse;
import com.custom.app.web.dto.response.ProductResponse;
import com.custom.app.web.mapper.ProductResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductServices {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryUseCase getCategoryUseCase;
    private final ListProductUseCase listProductUseCase;
    private final ListImageUseCase listImageUseCase;
    private final ImageServices imageServices;
    private final ProductResponseMapper productResponseMapper;

    @Autowired
    public ProductServices(
            CreateProductUseCase createProductUseCase,
            GetProductUseCase getProductUseCase,
            UpdateProductUseCase updateProductUseCase,
            CreateCategoryUseCase createCategoryUseCase,
            GetCategoryUseCase getCategoryUseCase,
            ListProductUseCase listProductUseCase, ListImageUseCase listImageUseCase,
            ImageServices imageServices,
            ProductResponseMapper productResponseMapper
    ) {
        this.createProductUseCase = createProductUseCase;
        this.getProductUseCase = getProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.createCategoryUseCase = createCategoryUseCase;
        this.getCategoryUseCase = getCategoryUseCase;
        this.listProductUseCase = listProductUseCase;
        this.listImageUseCase = listImageUseCase;
        this.imageServices = imageServices;
        this.productResponseMapper = productResponseMapper;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        List<Product> products = this.listProductUseCase.listAllProducts();
        return products.stream()
                .map(this.productResponseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoryProductsResponse> getCategoryProducts() {
        Map<String, List<Product>> productsByCategory = this.listProductUseCase.listProductsByCategory();

        return productsByCategory.entrySet().stream()
                .map(entry -> {
                    CategoryProductsResponse categoryDto = new CategoryProductsResponse();
                    categoryDto.setCategoryName(entry.getKey());

                    // Limita a 6 produtos por categoria
                    List<ProductResponse> productResponses = entry.getValue().stream()
                            .limit(6)
                            .map(this.productResponseMapper::toResponse)
                            .collect(Collectors.toList());

                    categoryDto.setProducts(productResponses);
                    return categoryDto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse createProduct(CreateProductRequest productRequest, List<MultipartFile> imageFiles) {
        Category category = this.findOrCreateCategory(productRequest.getCategoryName());

        Map<UUID, String> imagePaths = this.prepareImagePaths(imageFiles);

        Product product = this.productResponseMapper.toDomain(productRequest, imagePaths, category);

        try {
            if (imageFiles != null) {
                this.imageServices.writeImages(imageFiles, imagePaths);
            }
        } catch (Exception e) {
            this.imageServices.rollbackImages(imagePaths);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao salvar imagem física", e);
        }

        Product createdProduct = this.persistProductWithImages(product);

        return this.productResponseMapper.toResponse(createdProduct);
    }

    private Category findOrCreateCategory(String categoryName) {
        return this.createCategoryUseCase.findCategoryByName(categoryName)
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(categoryName);
                    return this.createCategoryUseCase.createCategory(newCategory);
                });
    }

    private Map<UUID, String> prepareImagePaths(List<MultipartFile> imageFiles) {
        if (imageFiles != null) {
            return this.imageServices.prepareImagePaths(ImageType.PRODUCT, imageFiles);
        } else {
            Map<UUID, String> fallback = new HashMap<>();
            fallback.put(UUID.randomUUID(), "");
            return fallback;
        }
    }

    @Transactional
    public Product persistProductWithImages(Product product) {
        Product createdProduct = this.createProductUseCase.createProduct(product);

        createdProduct.getImages().forEach(image -> image.setProductId(createdProduct.getId()));

        this.imageServices.saveImage(createdProduct.getImages());

        return createdProduct;
    }

    @Transactional
    public ProductResponse updateProduct(UpdateProductRequest request, List<MultipartFile> images, List<UUID> currentImageIds) {
        Product product = this.findProductOrThrow(request.getId());
        Category category = this.resolveCategory(request.getCategoryId(), request.getCategoryName());
        product.setCategory(category);
        this.updateAttributes(product, request);

        if (this.shouldSkipImageUpdate(images, currentImageIds)) {
            product.setImages(this.listImageUseCase.listImage(product.getId()));
            return this.saveProductAndReturnResponse(product);
        }

        this.validateImageLists(images, currentImageIds);

        List<Image> currentProductImages = this.listImageUseCase.listImage(product.getId());
        List<Image> toDelete = this.findImagesToDelete(currentProductImages, currentImageIds);
        List<Image> finalImages = this.findImagesToKeep(currentProductImages, currentImageIds);

        List<Image> newImageEntities = this.handleNewImages(product.getId(), images);
        this.saveNewImages(newImageEntities);

        this.deleteOldImagesWithRollback(toDelete, newImageEntities);

        finalImages.addAll(newImageEntities);
        product.setImages(finalImages);

        return this.saveProductAndReturnResponse(product);
    }

    private Product findProductOrThrow(UUID productId) {
        return this.getProductUseCase.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto não encontrado"));
    }

    private boolean shouldSkipImageUpdate(List<MultipartFile> images, List<UUID> currentImageIds) {
        return (images == null || images.isEmpty()) && (currentImageIds == null || currentImageIds.isEmpty());
    }

    private void validateImageLists(List<MultipartFile> images, List<UUID> currentImageIds) {
        if (images == null || currentImageIds == null || images.size() != currentImageIds.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A quantidade de imagens novas deve ser igual à quantidade de imagens a serem substituídas.");
        }
    }

    private List<Image> findImagesToDelete(List<Image> currentProductImages, List<UUID> currentImageIds) {
        return currentProductImages.stream()
                .filter(img -> currentImageIds.contains(img.getId()) && img.getPathImage() != null && !img.getPathImage().isBlank())
                .collect(Collectors.toList());
    }

    private List<Image> findImagesToKeep(List<Image> currentProductImages, List<UUID> currentImageIds) {
        return currentProductImages.stream()
                .filter(img -> !currentImageIds.contains(img.getId()))
                .collect(Collectors.toList());
    }

    private void saveNewImages(List<Image> newImageEntities) {
        this.imageServices.saveImage(newImageEntities);
    }

    private void deleteOldImagesWithRollback(List<Image> toDelete, List<Image> newImageEntities) {
        Map<UUID, String> rollbackMap = newImageEntities.stream()
                .collect(Collectors.toMap(Image::getId, Image::getPathImage));
        try {
            if (!toDelete.isEmpty()) {
                this.imageServices.deleteImages(toDelete);
            }
        } catch (Exception e) {
            if (!rollbackMap.isEmpty()) {
                this.imageServices.rollbackImages(rollbackMap);
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falha ao deletar imagens antigas", e);
        }
    }

    private ProductResponse saveProductAndReturnResponse(Product product) {
        Product updatedProduct = this.updateProductUseCase.updateProduct(product.getId(), product);
        return this.productResponseMapper.toResponse(updatedProduct);
    }

    private Category resolveCategory(UUID categoryId, String categoryName) {
        return this.getCategoryUseCase.findById(categoryId)
                .orElseGet(() -> {
                    Category cat = new Category();
                    cat.setName(categoryName);
                    return this.createCategoryUseCase.createCategory(cat);
                });
    }

    private void updateAttributes(Product product, UpdateProductRequest request) {
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setSku(request.getSku());
    }

    private List<Image> handleNewImages(UUID productId, List<MultipartFile> newImages) {
        if (newImages == null || newImages.isEmpty()) return List.of();

        Map<UUID, String> uuidToPath = this.imageServices.prepareImagePaths(ImageType.PRODUCT, newImages);
        List<Image> images = this.mapToImageEntities(newImages, uuidToPath, productId);
        this.imageServices.saveImage(images);

        try {
            this.imageServices.writeImages(newImages, uuidToPath);
        } catch (Exception e) {
            this.imageServices.rollbackImages(uuidToPath);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto salvo, mas falha ao salvar imagem física", e);
        }

        return images;
    }

    private List<Image> mapToImageEntities(List<MultipartFile> files, Map<UUID, String> uuidToPath, UUID productId) {
        List<UUID> uuids = new ArrayList<>(uuidToPath.keySet());
        return IntStream.range(0, files.size())
                .mapToObj(i -> {
                    UUID uuid = uuids.get(i);
                    String path = uuidToPath.get(uuid);
                    Image img = new Image();
                    img.setId(uuid);
                    img.setPathImage(path);
                    img.setType(ImageType.PRODUCT);
                    img.setProductId(productId);
                    return img;
                }).toList();
    }
}