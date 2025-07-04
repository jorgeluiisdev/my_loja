package com.custom.app.infra.banner.executor;

import com.custom.app.core.model.Banner;
import com.custom.app.core.model.User;
import com.custom.app.core.usecase.banner.UpdateBannerUseCase;
import com.custom.app.core.usecase.user.UpdateUserUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateBannerUseCaseAdapter implements UpdateBannerUseCase {

    @Override
    public Banner execute(UUID bannerId, Banner banner) {
        return null;
    }
}