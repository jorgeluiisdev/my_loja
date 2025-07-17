package com.custom.app.core.usecase.image;

import com.custom.app.core.model.Image;

import java.util.List;

public interface CreateImageUseCase {
    List<Image> createImage(List<Image> images);
}