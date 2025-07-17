package com.custom.app.persistence.repository;

import com.custom.app.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, UUID> {
    Optional<CategoryEntity> findByName(String name);
    @Query("SELECT c FROM CategoryEntity c LEFT JOIN FETCH c.products")
    List<CategoryEntity> findAllWithProducts();
}