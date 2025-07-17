package com.custom.app.persistence.adapter;

import com.custom.app.core.model.Image;
import com.custom.app.core.port.ImageRepository;
import com.custom.app.persistence.entity.ImageEntity;
import com.custom.app.persistence.mapepr.ImageMapper;
import com.custom.app.persistence.repository.ImageJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ImageRepositoryAdapter implements ImageRepository {

    private final ImageJpaRepository imageJpaRepository;
    private final ImageMapper imageMapper;

    @Autowired
    public ImageRepositoryAdapter(
            ImageJpaRepository imageJpaRepository,
            ImageMapper imageMapper
    ) {
        this.imageJpaRepository = imageJpaRepository;
        this.imageMapper = imageMapper;
    }

    @Transactional
    @Override
    public List<Image> save(List<Image> images) {
        for (Image img : images) {
            if (img.getId() == null) {
                throw new IllegalArgumentException("Id da imagem não deve ser null");
            }
        }

        List<ImageEntity> imageEntities = images.stream()
                .map(this.imageMapper::toEntity)
                .toList();

        List<ImageEntity> savedEntities = this.imageJpaRepository.saveAll(imageEntities);

        return savedEntities.stream()
                .map(this.imageMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Image> findById(UUID id) {
        return this.imageJpaRepository.findById(id)
                .map(this.imageMapper::toDomain);
    }
}