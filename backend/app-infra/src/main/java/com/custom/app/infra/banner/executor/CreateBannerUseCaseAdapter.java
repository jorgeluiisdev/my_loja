package com.custom.app.infra.banner.executor;

import com.custom.app.core.model.Banner;
import com.custom.app.core.usecase.banner.CreateBannerUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreateBannerUseCaseAdapter implements CreateBannerUseCase {
    @Override
    public Banner execute(Banner banner) {
        return null;
    }
}