package com.custom.app.core.port;

import com.custom.app.core.model.Image;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageRepository {
    Image save(Image image);
    List<Image> save(List<Image> images);
    void delete(UUID existingImageId);
    Optional<Image> findById(UUID imageId);
    List<Image> listImage(UUID existingProductId);
}