package com.custom.app.infra.category.executor;

import com.custom.app.core.model.Category;
import com.custom.app.core.usecase.category.GetCategoryUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetCategoryUseCaseAdapter implements GetCategoryUseCase {
    @Override
    public Optional<Category> execute(UUID categoryId) {
        return Optional.empty();
    }
}