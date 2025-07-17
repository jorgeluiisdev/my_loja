package com.custom.app.core.usecase.category;

import com.custom.app.core.model.Category;

import java.util.Optional;

public interface CreateCategoryUseCase {
    Category createCategory(Category category);
    Optional<Category> findCategoryByName(String categoryName);
}