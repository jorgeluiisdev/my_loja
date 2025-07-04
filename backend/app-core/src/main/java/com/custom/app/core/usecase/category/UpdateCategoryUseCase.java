package com.custom.app.core.usecase.category;

import com.custom.app.core.model.Category;

import java.util.UUID;

public interface UpdateCategoryUseCase {
    Category execute(UUID categoryId, Category category);
}