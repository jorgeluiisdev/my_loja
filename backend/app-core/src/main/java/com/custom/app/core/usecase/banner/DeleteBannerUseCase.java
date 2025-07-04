package com.custom.app.core.usecase.banner;

import java.util.UUID;

public interface DeleteBannerUseCase {
    void execute(UUID bannerId);
}