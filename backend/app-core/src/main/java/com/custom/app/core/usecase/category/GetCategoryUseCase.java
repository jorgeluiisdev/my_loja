package com.custom.app.core.usecase.category;

import com.custom.app.core.model.Category;

import java.util.Optional;
import java.util.UUID;

public interface GetCategoryUseCase {
    Optional<Category> execute(UUID categoryId);
}