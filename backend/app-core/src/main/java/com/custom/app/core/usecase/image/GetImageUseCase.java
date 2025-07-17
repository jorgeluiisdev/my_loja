package com.custom.app.core.usecase.image;

import com.custom.app.core.model.Image;

import java.util.Optional;
import java.util.UUID;

public interface GetImageUseCase {
    Optional<Image> findById(UUID imageId);
}