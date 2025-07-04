package com.custom.app.core.usecase.category;

import com.custom.app.core.model.Category;

public interface CreateCategoryUseCase {
    Category execute(Category category);
}