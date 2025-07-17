package com.custom.app.persistence.mapepr;

import com.custom.app.core.model.Category;
import com.custom.app.core.model.Image;
import com.custom.app.core.model.Product;
import com.custom.app.persistence.entity.CategoryEntity;
import com.custom.app.persistence.entity.ImageEntity;
import com.custom.app.persistence.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    private final ImageMapper imageMapper;
    private final CategoryMapper categoryMapper;

    @Autowired
    public ProductMapper(
            ImageMapper imageMapper,
            CategoryMapper categoryMapper
    ) {
        this.imageMapper = imageMapper;
        this.categoryMapper = categoryMapper;
    }

    public ProductEntity toEntity(Product product) {
        ProductEntity productEntity = new ProductEntity();

        if (product.getId() != null) {
            productEntity.setId(product.getId());
        }
        productEntity.setTitle(product.getTitle());
        productEntity.setDescription(product.getDescription());
        productEntity.setPrice(product.getPrice());
        productEntity.setSku(product.getSku());

        List<ImageEntity> images = product.getImages().stream()
                .map(this.imageMapper::toEntity)
                .collect(Collectors.toList());

        productEntity.setImages(images);
        if (product.getCategory() != null) {
            CategoryEntity categoryEntity = this.categoryMapper.toEntity(product.getCategory());
            productEntity.setCategory(categoryEntity);
        } else {
            productEntity.setCategory(null);
        }

        return productEntity;
    }

    public Product toDomain(ProductEntity productEntity) {
        Product product = new Product();

        product.setId(productEntity.getId());
        product.setTitle(productEntity.getTitle());
        product.setDescription(productEntity.getDescription());
        product.setPrice(productEntity.getPrice());
        product.setSku(productEntity.getSku());

        List<Image> images = productEntity.getImages().stream()
                .map(this.imageMapper::toDomain)
                .collect(Collectors.toList());

        product.setImages(images);
        Category category = this.categoryMapper.toDomain(productEntity.getCategory());
        product.setCategory(category);

        return product;
    }
}