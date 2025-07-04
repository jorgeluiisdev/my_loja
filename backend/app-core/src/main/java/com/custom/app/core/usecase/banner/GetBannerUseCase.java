package com.custom.app.core.usecase.banner;

import com.custom.app.core.model.Banner;

import java.util.Optional;
import java.util.UUID;

public interface GetBannerUseCase {
    Optional<Banner> execute(UUID bannerId);
}