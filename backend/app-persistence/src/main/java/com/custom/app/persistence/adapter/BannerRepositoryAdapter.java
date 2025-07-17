package com.custom.app.persistence.adapter;

import com.custom.app.core.model.Banner;
import com.custom.app.core.port.BannerRepository;
import com.custom.app.persistence.entity.BannerEntity;
import com.custom.app.persistence.mapepr.BannerMapper;
import com.custom.app.persistence.repository.BannerJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class BannerRepositoryAdapter implements BannerRepository {

    private final BannerJpaRepository bannerJpaRepository;
    private final BannerMapper mapper;

    @Autowired
    public BannerRepositoryAdapter(
            BannerJpaRepository bannerJpaRepository,
            BannerMapper mapper
    ) {
        this.bannerJpaRepository = bannerJpaRepository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public Banner save(Banner banner) {
        if (banner.getId() != null) {
            throw new IllegalArgumentException("Novo objeto não deve conter Id");
        }
        BannerEntity entity = this.mapper.toEntity(banner);
        BannerEntity savedBanner = this.bannerJpaRepository.save(entity);
        return this.mapper.toDomain(savedBanner);
    }

    @Transactional
    @Override
    public Banner update(UUID existingBannerId, Banner banner) {
        if (existingBannerId == null || existingBannerId.equals(banner.getId())) {
            throw new EntityNotFoundException("Banner não encontrado");
        }
        BannerEntity entity = this.mapper.toEntity(banner);
        BannerEntity savedBanner = this.bannerJpaRepository.save(entity);
        return this.mapper.toDomain(savedBanner);
    }

    @Transactional
    @Override
    public void delete(UUID existingBannerId) {
        if (existingBannerId == null) {
            throw new EntityNotFoundException("O Id do banner não pode ser nulo");
        }
        this.bannerJpaRepository.deleteById(existingBannerId);
    }

    @Override
    public List<Banner> findAll() {
        List<BannerEntity> entities = this.bannerJpaRepository.findAll();
        return entities.stream()
                .map(this.mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Banner> findById(UUID bannerId) {
        return this.bannerJpaRepository.findById(bannerId)
                .map(this.mapper::toDomain);
    }
}