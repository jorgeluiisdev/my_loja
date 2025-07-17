package com.custom.app.web.services;

import com.custom.app.core.model.Category;
import com.custom.app.core.model.Image;
import com.custom.app.core.model.ImageType;
import com.custom.app.core.model.Product;
import com.custom.app.core.usecase.category.CreateCategoryUseCase;
import com.custom.app.core.usecase.category.GetCategoryUseCase;
import com.custom.app.core.usecase.image.GetImageUseCase;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final GetImageUseCase getImageUseCase;
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
            GetImageUseCase getImageUseCase,
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
        this.getImageUseCase = getImageUseCase;
        this.imageServices = imageServices;
        this.productResponseMapper = productResponseMapper;
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = this.listProductUseCase.listAllProducts();
        return products.stream()
                .map(this.productResponseMapper::toResponse)
                .collect(Collectors.toList());
    }

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

    public ProductResponse createProduct(CreateProductRequest productRequest, List<MultipartFile> imageFiles) {
        Optional<Category> existingCategory = this.createCategoryUseCase.findCategoryByName(productRequest.getCategoryName());

        Category category = existingCategory.orElseGet(() -> {
            Category newCat = new Category();
            newCat.setName(productRequest.getCategoryName());
            return this.createCategoryUseCase.createCategory(newCat);
        });

        Map<UUID, String> imagens = new HashMap<>();
        if (imageFiles != null) {
            imagens = this.imageServices.prepareImagePaths(ImageType.PRODUCT, imageFiles);
        } else {
            imagens.put(UUID.randomUUID(), "/temp");
        }

        Product product = this.productResponseMapper.toDomain(productRequest, imagens, category);

        Product createdProduct = this.createProductUseCase.createProduct(product);

        createdProduct.getImages().forEach(image -> image.setProductId(createdProduct.getId()));

        this.imageServices.saveImage(createdProduct.getImages());

        if (imageFiles != null) {
            try {
                this.imageServices.writeImages(imageFiles, imagens);
            } catch (Exception e) {
                this.imageServices.rollbackImages(imagens);
                throw new RuntimeException("Produto salvo, mas falha ao salvar imagem física", e);
            }
        }

        return this.productResponseMapper.toResponse(createdProduct);
    }

    public ProductResponse updateProduct(UpdateProductRequest request, List<MultipartFile> newImages, List<UUID> currentImageIds) {
        Product product = this.getProductUseCase.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Category category = this.resolveCategory(request.getCategoryId(), request.getCategoryName());
        product.setCategory(category);

        this.updateAttributes(product, request);

        List<Image> retainedImages = this.fetchRetainedImages(currentImageIds);

        List<Image> newImageEntities = this.handleNewImages(product.getId(), newImages);

        try {
            List<Image> currentProductImages = this.listImageUseCase.listImage(product.getId());

            Set<UUID> retainedIds = currentImageIds != null ? new HashSet<>(currentImageIds) : Collections.emptySet();

            List<Image> toDelete = new ArrayList<>();
            for (Image img : currentProductImages) {
                if (!retainedIds.contains(img.getId())) {
                    toDelete.add(img);
                }
            }

            if (!toDelete.isEmpty()) {
                this.imageServices.deleteImages(toDelete);
            }
        } catch (Exception e) {
            if (!newImageEntities.isEmpty()) {
                Map<UUID, String> rollbackMap = newImageEntities.stream()
                        .collect(Collectors.toMap(Image::getId, Image::getPathImage));
                this.imageServices.rollbackImages(rollbackMap);
            }
            throw new RuntimeException("Falha ao deletar imagens antigas", e);
        }

        retainedImages.addAll(newImageEntities);
        product.setImages(retainedImages);

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

    private List<Image> fetchRetainedImages(List<UUID> currentImageIds) {
        if (currentImageIds == null) return new ArrayList<>();
        List<Image> images = new ArrayList<>();
        for (UUID id : currentImageIds) {
            this.getImageUseCase.findById(id).ifPresent(images::add);
        }
        return images;
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
            throw new RuntimeException("Produto salvo, mas falha ao salvar imagem física", e);
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