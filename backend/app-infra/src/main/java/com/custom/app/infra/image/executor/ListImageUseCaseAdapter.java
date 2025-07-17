package com.custom.app.infra.image.executor;

import com.custom.app.core.model.Image;
import com.custom.app.core.port.ImageRepository;
import com.custom.app.core.usecase.image.ListImageUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ListImageUseCaseAdapter implements ListImageUseCase {

    private final ImageRepository imageRepository;

    @Autowired
    public ListImageUseCaseAdapter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public List<Image> listImage(UUID existingProductId) {
        return this.imageRepository.listImage(existingProductId);
    }
}
