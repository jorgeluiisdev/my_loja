package com.custom.app.persistence.mapepr;

import com.custom.app.core.model.Product;
import com.custom.app.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductEntity toEntity(Product product) {
        ProductEntity productEntity = new ProductEntity();

        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setPrice(product.getPrice());
        productEntity.setImageUrl(product.getImageUrl());
        productEntity.setSku(product.getSku());

        return productEntity;
    }

    public Product toModel(ProductEntity productEntity) {
        Product product = new Product();

        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setPrice(productEntity.getPrice());
        product.setImageUrl(productEntity.getImageUrl());
        product.setSku(productEntity.getSku());

        return product;
    }
}