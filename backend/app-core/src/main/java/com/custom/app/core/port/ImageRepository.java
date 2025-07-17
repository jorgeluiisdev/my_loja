package com.custom.app.core.port;

import com.custom.app.core.model.Image;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageRepository {
    List<Image> save(List<Image> images);
    Optional<Image> findById(UUID imageId);
}