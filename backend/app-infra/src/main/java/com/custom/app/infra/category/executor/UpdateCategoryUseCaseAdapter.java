package com.custom.app.infra.category.executor;

import com.custom.app.core.model.Category;
import com.custom.app.core.usecase.category.UpdateCategoryUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateCategoryUseCaseAdapter implements UpdateCategoryUseCase {

    @Override
    public Category execute(UUID categoryId, Category category) {
        return null;
    }
}