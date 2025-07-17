package com.custom.app.infra.category.executor;

import com.custom.app.core.model.Category;
import com.custom.app.core.usecase.category.CreateCategoryUseCase;
import com.custom.app.persistence.adapter.CategoryRepositoryAdapter;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CreateCategoryUseCaseAdapter implements CreateCategoryUseCase {

    private final CategoryRepositoryAdapter categoryRepositoryAdapter;

    public CreateCategoryUseCaseAdapter(CategoryRepositoryAdapter categoryRepositoryAdapter) {
        this.categoryRepositoryAdapter = categoryRepositoryAdapter;
    }

    @Override
    public Category createCategory(Category category) {
        return this.categoryRepositoryAdapter.save(category);
    }

    @Override
    public Optional<Category> findCategoryByName(String categoryName) {
        return this.categoryRepositoryAdapter.findByName(categoryName);
    }

}