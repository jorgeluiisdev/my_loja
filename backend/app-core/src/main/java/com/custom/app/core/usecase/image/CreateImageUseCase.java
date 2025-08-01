package com.custom.app.core.usecase.image;

import com.custom.app.core.model.Image;

import java.util.List;

public interface CreateImageUseCase {
    Image createImage(Image image);
    List<Image> createImages(List<Image> images);
}