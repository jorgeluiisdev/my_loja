package com.custom.app.persistence.repository;

import com.custom.app.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {
    Optional<ProductEntity> findByTitle(String title);
    Optional<ProductEntity> findBySku(String sku);
    @Query("SELECT p FROM ProductEntity p JOIN FETCH p.category")
    List<ProductEntity> findAllWithCategory();
}