package com.custom.app.infra.image.executor;

import com.custom.app.core.model.Image;
import com.custom.app.core.port.ImageRepository;
import com.custom.app.core.usecase.image.GetImageUseCase;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Component
public class GetImageUseCaseAdapter implements GetImageUseCase {

    private final ImageRepository imageRepository;

    public GetImageUseCaseAdapter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Optional<Image> findById(UUID imageId) {
        return this.imageRepository.findById(imageId);
    }
}