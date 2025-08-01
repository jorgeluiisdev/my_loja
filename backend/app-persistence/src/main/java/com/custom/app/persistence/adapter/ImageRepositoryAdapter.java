package com.custom.app.persistence.adapter;

import com.custom.app.core.model.Image;
import com.custom.app.core.port.ImageRepository;
import com.custom.app.persistence.entity.ImageEntity;
import com.custom.app.persistence.mapepr.ImageMapper;
import com.custom.app.persistence.repository.ImageJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ImageRepositoryAdapter implements ImageRepository {

    private final ImageJpaRepository jpaRepository;
    private final ImageMapper imageMapper;

    @Autowired
    public ImageRepositoryAdapter(
            ImageJpaRepository imageJpaRepository,
            ImageMapper imageMapper
    ) {
        this.jpaRepository = imageJpaRepository;
        this.imageMapper = imageMapper;
    }

    @Override
    public Image save(Image image) {
        return Optional.ofNullable(image)
                .filter(img -> img.getId() != null)
                .map(this.imageMapper::toEntity)
                .map(this.jpaRepository::save)
                .map(this.imageMapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Id da imagem não deve ser null"));
    }

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

        List<ImageEntity> savedEntities = this.jpaRepository.saveAll(imageEntities);

        return savedEntities.stream()
                .map(this.imageMapper::toDomain)
                .toList();
    }

    @Override
    public void delete(UUID existingImageId) {
        if (existingImageId == null) {
            throw new EntityNotFoundException("O Id do produto não pode ser nulo");
        }
        this.jpaRepository.deleteById(existingImageId);
    }

    @Override
    public Optional<Image> findById(UUID id) {
        return this.jpaRepository.findById(id)
                .map(this.imageMapper::toDomain);
    }

    @Override
    public List<Image> listImage(UUID existingProductId) {
        return this.jpaRepository.findByProductId(existingProductId)
                .stream().map(this.imageMapper::toDomain)
                .collect(Collectors.toList());
    }

}