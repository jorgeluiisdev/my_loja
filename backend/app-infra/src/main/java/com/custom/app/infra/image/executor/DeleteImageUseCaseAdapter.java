package com.custom.app.infra.image.executor;

import com.custom.app.core.port.ImageRepository;
import com.custom.app.core.usecase.image.DeleteImageUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteImageUseCaseAdapter implements DeleteImageUseCase {

    private final ImageRepository imageRepository;

    @Autowired
    public DeleteImageUseCaseAdapter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void deleteImage(UUID existingImageId) {
        this.imageRepository.delete(existingImageId);
    }
}