package com.custom.app.persistence.adapter;

import com.custom.app.core.model.Product;
import com.custom.app.core.port.ProductRepository;
import com.custom.app.persistence.entity.ProductEntity;
import com.custom.app.persistence.mapepr.ProductMapper;
import com.custom.app.persistence.repository.CategoryJpaRepository;
import com.custom.app.persistence.repository.ProductJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductRepositoryAdapter implements ProductRepository {

    private final CategoryJpaRepository categoryJpaRepository;
    private final ProductJpaRepository jpaRepository;
    private final ProductMapper mapper;

    @Autowired
    public ProductRepositoryAdapter(
            CategoryJpaRepository categoryJpaRepository,
            ProductJpaRepository productRepository,
            ProductMapper productMapper
    ) {
        this.categoryJpaRepository = categoryJpaRepository;
        this.jpaRepository = productRepository;
        this.mapper = productMapper;
    }

    @Transactional
    @Override
    public Product save(Product product) {
        if (product.getId() != null) {
            throw new IllegalArgumentException("Novo objeto não deve conter Id");
        }

        ProductEntity entity = this.mapper.toEntity(product);

        if (product.getCategory() != null && product.getCategory().getId() != null) {
            this.categoryJpaRepository.findById(product.getCategory().getId())
                    .ifPresent(entity::setCategory);
        }

        ProductEntity savedEntity = this.jpaRepository.save(entity);
        return this.mapper.toDomain(savedEntity);
    }

    @Transactional
    @Override
    public Product update(UUID existingProductId, Product product) {
        if (product.getId() == null) {
            throw new EntityNotFoundException("Produto não encontrado");
        }

        ProductEntity entity = this.mapper.toEntity(product);

        if (product.getCategory() != null && product.getCategory().getId() != null) {
            this.categoryJpaRepository.findById(product.getCategory().getId())
                    .ifPresent(entity::setCategory);
        }

        ProductEntity updatedEntity = this.jpaRepository.save(entity);
        return this.mapper.toDomain(updatedEntity);
    }

    @Transactional
    @Override
    public void delete(UUID existingProductId) {
        if (existingProductId == null) {
            throw new EntityNotFoundException("O Id do produto não pode ser nulo");
        }
        this.jpaRepository.deleteById(existingProductId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        List<ProductEntity> entities = this.jpaRepository.findAll();
        return entities.stream()
                .map(this.mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Map<String, List<Product>> findAllByCategory() {
        List<ProductEntity> entities = this.jpaRepository.findAllWithCategory();

        return entities.stream()
                .collect(Collectors.groupingBy(
                        entity -> entity.getCategory().getName(),
                        Collectors.mapping(this.mapper::toDomain, Collectors.toList()
                        )
                ));
    }

    @Transactional(readOnly = true)
    public Optional<Product> findById(UUID productId) {
        return jpaRepository.findById(productId)
                .map(productEntity -> {
                    Hibernate.initialize(productEntity.getImages());
                    return mapper.toDomain(productEntity);
                });
    }

    @Override
    public Optional<Product> findBySku(String productSku) {
        return this.jpaRepository.findBySku(productSku)
                .map(this.mapper::toDomain);
    }

    @Override
    public Optional<Product> findByTitle(String productTitle) {
        return this.jpaRepository.findByTitle(productTitle)
                .map(this.mapper::toDomain);
    }
}