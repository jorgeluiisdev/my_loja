package com.custom.app.infra.category.executor;

import com.custom.app.core.model.Category;
import com.custom.app.core.usecase.category.ListCategoryUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListCategoryUseCaseAdapter implements ListCategoryUseCase {
    @Override
    public List<Category> execute() {
        return List.of();
    }
}