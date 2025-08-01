package com.custom.app.web.mapper;

import com.custom.app.core.model.Category;
import com.custom.app.core.model.Image;
import com.custom.app.core.model.ImageType;
import com.custom.app.core.model.Product;
import com.custom.app.persistence.mapepr.ImageMapper;
import com.custom.app.web.dto.request.CreateProductRequest;
import com.custom.app.web.dto.request.UpdateProductRequest;
import com.custom.app.web.dto.response.ProductResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductResponseMapper {

    public ProductResponse toResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();

        productResponse.setTitle(product.getTitle());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        for (Image images :product.getImages()) {
            images.setPathImage("");
            productResponse.setImagens(product.getImages());
        }
        productResponse.setSku(product.getSku());
        productResponse.setCategoryId(product.getCategory().getId().toString());

        return productResponse;
    }

    public Product toDomain(CreateProductRequest productRequest, Map<UUID, String> images, Category category) {
        Product product = new Product();

        product.setTitle(productRequest.getTitle());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setSku(productRequest.getSku());
        product.setCategory(category);

        List<Image> imageList = images.entrySet().stream()
                .map(entry -> {
                    Image image = new Image();
                    if (entry.getKey() != null) {
                        image.setId(entry.getKey());
                        image.setPathImage(entry.getValue());
                        image.setType(ImageType.PRODUCT);
                    } else {
                        image.setId(UUID.randomUUID());
                        image.setPathImage(UUID.randomUUID().toString());
                        image.setType(ImageType.PRODUCT);
                    }
                    return image;
                })
                .collect(Collectors.toList());

        product.setImages(imageList);

        return product;
    }

    public Product toDomain(UpdateProductRequest productRequest, Map<UUID, String> images, Category category) {
        Product product = new Product();

        product.setTitle(productRequest.getTitle());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setSku(productRequest.getSku());
        product.setCategory(category);

        List<Image> imageList = images.entrySet().stream()
                .map(entry -> {
                    Image image = new Image();
                    if (entry.getKey() != null) {
                        image.setId(entry.getKey());
                        image.setPathImage(entry.getValue());
                        image.setType(ImageType.PRODUCT);
                    } else {
                        image.setId(UUID.randomUUID());
                        image.setPathImage(UUID.randomUUID().toString());
                        image.setType(ImageType.PRODUCT);
                    }
                    return image;
                })
                .collect(Collectors.toList());

        product.setImages(imageList);

        return product;
    }


}