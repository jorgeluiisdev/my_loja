package com.custom.app.core.port;

import com.custom.app.core.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {
    Category save(Category category);
    Category update(UUID existingCategoryId, Category category);
    void delete(UUID existingCategoryId);
    List<Category> findAll();
    Optional<Category> findById(UUID categoryId);
    Optional<Category> findByName(String name);
}