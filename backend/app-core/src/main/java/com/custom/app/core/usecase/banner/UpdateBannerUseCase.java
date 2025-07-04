package com.custom.app.core.usecase.banner;

import com.custom.app.core.model.Banner;

import java.util.UUID;

public interface UpdateBannerUseCase {
    Banner execute(UUID bannerId, Banner banner);
}