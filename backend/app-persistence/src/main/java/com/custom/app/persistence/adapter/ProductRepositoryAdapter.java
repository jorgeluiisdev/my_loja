package com.custom.app.persistence.adapter;

import com.custom.app.core.model.Product;
import com.custom.app.core.port.ProductRepository;
import com.custom.app.persistence.entity.ProductEntity;
import com.custom.app.persistence.mapepr.ProductMapper;
import com.custom.app.persistence.repository.ProductJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductJpaRepository jpaRepository;
    private final ProductMapper mapper;

    @Autowired
    public ProductRepositoryAdapter(
            ProductJpaRepository productRepository,
            ProductMapper productMapper
    ) {
        this.jpaRepository = productRepository;
        this.mapper = productMapper;
    }

    @Transactional
    @Override
    public Product save(Product product) {
        ProductEntity entity = this.mapper.toEntity(product);
        ProductEntity savedEntity = this.jpaRepository.save(entity);
        return this.mapper.toModel(savedEntity);
    }

    @Transactional
    @Override
    public Product update(Product product) {
        ProductEntity entity = this.mapper.toEntity(product);
        ProductEntity updatedEntity = this.jpaRepository.save(entity);
        return this.mapper.toModel(updatedEntity);
    }

    @Transactional
    @Override
    public void delete(UUID existingProductId) {
        this.jpaRepository.deleteById(existingProductId);
    }

    @Override
    public List<Product> findAll() {
        List<ProductEntity> entities = this.jpaRepository.findAll();
        return entities.stream()
                .map(this.mapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<Product>> findAllByCategory() {
        List<ProductEntity> entities = this.jpaRepository.findAllWithCategory();

        return entities.stream()
                .collect(Collectors.groupingBy(
                        entity -> entity.getCategory().getName(),
                        Collectors.mapping(
                                this.mapper::toModel,
                                Collectors.toList()
                        )
                ));
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        return this.jpaRepository.findById(productId)
                .map(this.mapper::toModel);
    }

    @Override
    public Optional<Product> findBySku(String productSku) {
        return this.jpaRepository.findBySku(productSku)
                .map(this.mapper::toModel);
    }

    @Override
    public Optional<Product> findByTitle(String productTitle) {
        return this.jpaRepository.findByTitle(productTitle)
                .map(this.mapper::toModel);
    }
}