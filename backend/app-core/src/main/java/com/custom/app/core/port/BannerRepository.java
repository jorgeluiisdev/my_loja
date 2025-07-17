package com.custom.app.core.port;

import com.custom.app.core.model.Banner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BannerRepository {
    Banner save(Banner banner);
    Banner update(UUID existingBannerId, Banner banner);
    void delete(UUID existingBannerId);
    List<Banner> findAll();
    Optional<Banner> findById(UUID bannerId);
}