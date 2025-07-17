package com.custom.app.core.usecase.image;

import java.util.UUID;

public interface DeleteImageUseCase {
    void deleteImage(UUID existingImageId);
}