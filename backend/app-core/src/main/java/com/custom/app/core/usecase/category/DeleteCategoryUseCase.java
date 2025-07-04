package com.custom.app.core.usecase.category;

import java.util.UUID;

public interface DeleteCategoryUseCase {
    void execute(UUID categoryId);
}