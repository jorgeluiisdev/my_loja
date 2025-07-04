package com.custom.app.core.port;

import com.custom.app.core.model.Banner;

import java.util.List;
import java.util.UUID;

public interface BannerRepository {
    Banner save(Banner banner);
    Banner update(Banner banner);
    void delete(Banner banner);
    List<Banner> findAll();
    Banner findById(UUID bannerId);
}