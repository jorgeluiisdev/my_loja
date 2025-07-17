package com.custom.app.infra.category.executor;

import com.custom.app.core.model.Category;
import com.custom.app.core.port.CategoryRepository;
import com.custom.app.core.usecase.category.GetCategoryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetCategoryUseCaseAdapter implements GetCategoryUseCase {

    private final CategoryRepository categoryRepository;

    @Autowired
    public GetCategoryUseCaseAdapter(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> findById(UUID categoryId) {
        return this.categoryRepository.findById(categoryId);
    }
}