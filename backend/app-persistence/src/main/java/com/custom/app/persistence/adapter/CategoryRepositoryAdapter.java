package com.custom.app.persistence.adapter;

import com.custom.app.core.model.Category;
import com.custom.app.core.port.CategoryRepository;
import com.custom.app.persistence.entity.CategoryEntity;
import com.custom.app.persistence.mapepr.CategoryMapper;
import com.custom.app.persistence.repository.CategoryJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CategoryRepositoryAdapter implements CategoryRepository {

    private final CategoryJpaRepository jpaRepository;
    private final CategoryMapper mapper;

    @Autowired
    public CategoryRepositoryAdapter(
            CategoryJpaRepository categoryJpaRepository,
            CategoryMapper categoryMapper
    ) {
        this.jpaRepository = categoryJpaRepository;
        this.mapper = categoryMapper;
    }

    @Override
    public Category save(Category category) {
        if (category.getId() != null) {
            throw new IllegalArgumentException("Novo objeto não deve conter Id");
        }
        CategoryEntity categoryEntity = this.mapper.toEntity(category);
        CategoryEntity savedCategoryEntity = this.jpaRepository.save(categoryEntity);
        return this.mapper.toDomain(savedCategoryEntity);
    }

    @Override
    public Category update(UUID existingCategoryId, Category category) {
        if (existingCategoryId == null || !existingCategoryId.equals(category.getId())) {
            throw new EntityNotFoundException("Categoria não encontrada");
        }
        CategoryEntity categoryEntity = this.mapper.toEntity(category);
        CategoryEntity savedCategoryEntity = this.jpaRepository.save(categoryEntity);
        return this.mapper.toDomain(savedCategoryEntity);
    }

    @Override
    public void delete(UUID existingCategoryId) {
        if (existingCategoryId == null) {
            throw new EntityNotFoundException("O Id da categoria não pode ser nulo");
        }
        this.jpaRepository.deleteById(existingCategoryId);
    }

    @Override
    public List<Category> findAll() {
        List<CategoryEntity> entities = this.jpaRepository.findAll();
        return entities.stream()
                .map(this.mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Category> findById(UUID categoryId) {
        return this.jpaRepository.findById(categoryId)
                .map(this.mapper::toDomain);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return this.jpaRepository.findByName(name)
                .map(this.mapper::toDomain);
    }
}