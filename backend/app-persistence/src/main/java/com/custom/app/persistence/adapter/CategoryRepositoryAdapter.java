package com.custom.app.persistence.adapter;

import com.custom.app.core.model.Category;
import com.custom.app.core.port.CategoryRepository;
import com.custom.app.persistence.entity.CategoryEntity;
import com.custom.app.persistence.entity.ProductEntity;
import com.custom.app.persistence.mapepr.CategoryMapper;
import com.custom.app.persistence.repository.CategoryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CategoryRepositoryAdapter implements CategoryRepository {

    private final CategoryJpaRepository jpaRepository;
    private final CategoryMapper mapper;

    public CategoryRepositoryAdapter(
            CategoryJpaRepository categoryJpaRepository,
            CategoryMapper categoryMapper
    ) {
        this.jpaRepository = categoryJpaRepository;
        this.mapper = categoryMapper;
    }

    @Override
    public Category save(Category category) {
        return null;
    }

    @Override
    public Category update(Category category) {
        return null;
    }

    @Override
    public void delete(Category category) {

    }

    @Override
    public List<Category> findAll() {
        List<CategoryEntity> entities = this.jpaRepository.findAll();
        return entities.stream()
                .map(this.mapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Category findById(UUID categoryId) {
        return null;
    }

    @Override
    public Optional<Category> findByName(String name) {
        return Optional.empty();
    }
}
