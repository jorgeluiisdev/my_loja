package com.custom.app.persistence.repository;

import com.custom.app.persistence.entity.BannerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BannerJpaRepository extends JpaRepository<BannerEntity, UUID> {
}
