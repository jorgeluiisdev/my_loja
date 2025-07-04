package com.custom.app.persistence.mapepr;

import com.custom.app.core.model.Category;
import com.custom.app.persistence.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryEntity toEntity(Category category) {
        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setName(categoryEntity.getName());

        return categoryEntity;
    }

    public Category toModel(CategoryEntity categoryEntity) {
        Category category = new Category();

        category.setName(categoryEntity.getName());

        return category;
    }
}