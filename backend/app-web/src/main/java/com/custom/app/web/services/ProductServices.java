package com.custom.app.web.services;

import com.custom.app.core.model.Category;
import com.custom.app.core.model.ImageType;
import com.custom.app.core.model.Product;
import com.custom.app.core.usecase.category.CreateCategoryUseCase;
import com.custom.app.core.usecase.product.CreateProductUseCase;
import com.custom.app.core.usecase.product.ListProductUseCase;
import com.custom.app.web.dto.request.CreateProductRequest;
import com.custom.app.web.dto.response.CategoryProductsResponse;
import com.custom.app.web.dto.response.ProductResponse;
import com.custom.app.web.mapper.ProductResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServices {

    private final CreateProductUseCase createProductUseCase;
    private final CreateCategoryUseCase createCategoryUseCase;
    private final ListProductUseCase listProductUseCase;
    private final ImageServices imageServices;
    private final ProductResponseMapper productResponseMapper;

    @Autowired
    public ProductServices(
            CreateProductUseCase createProductUseCase,
            CreateCategoryUseCase createCategoryUseCase,
            ListProductUseCase listProductUseCase,
            ImageServices imageServices,
            ProductResponseMapper productResponseMapper
    ) {
        this.createProductUseCase = createProductUseCase;
        this.createCategoryUseCase = createCategoryUseCase;
        this.listProductUseCase = listProductUseCase;
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
}