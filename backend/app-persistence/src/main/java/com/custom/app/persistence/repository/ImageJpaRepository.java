package com.custom.app.persistence.repository;

import com.custom.app.persistence.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImageJpaRepository extends JpaRepository<ImageEntity, UUID> {
    List<ImageEntity> findByProductId(UUID productId);
}