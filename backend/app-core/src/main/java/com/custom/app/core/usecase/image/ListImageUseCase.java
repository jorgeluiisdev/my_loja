package com.custom.app.core.usecase.image;

import com.custom.app.core.model.Image;

import java.util.List;
import java.util.UUID;

public interface ListImageUseCase {
    List<Image> listImage(UUID productId);
}