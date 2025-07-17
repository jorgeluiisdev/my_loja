package com.custom.app.persistence.mapepr;

import com.custom.app.core.model.Category;
import com.custom.app.persistence.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryEntity toEntity(Category category) {
        CategoryEntity categoryEntity = new CategoryEntity();

        if (category.getId() != null) {
            categoryEntity.setId(category.getId());
        }
        categoryEntity.setName(category.getName().toUpperCase()); //todas as categorias sempre uppercase

        return categoryEntity;
    }

    public Category toDomain(CategoryEntity categoryEntity) {
        Category category = new Category();

        category.setId(categoryEntity.getId());
        category.setName(categoryEntity.getName().toUpperCase());

        return category;
    }
}