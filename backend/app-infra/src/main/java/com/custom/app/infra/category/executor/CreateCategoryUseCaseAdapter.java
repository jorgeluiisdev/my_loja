package com.custom.app.infra.category.executor;

import com.custom.app.core.model.Category;
import com.custom.app.core.usecase.category.CreateCategoryUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreateCategoryUseCaseAdapter implements CreateCategoryUseCase {
    @Override
    public Category execute(Category category) {
        return null;
    }
}