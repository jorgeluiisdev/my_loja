package com.custom.app.infra.banner.executor;

import com.custom.app.core.model.Banner;
import com.custom.app.core.usecase.banner.GetBannerUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetBannerUseCaseAdapter implements GetBannerUseCase {

    @Override
    public Optional<Banner> execute(UUID bannerId) {
        return Optional.empty();
    }
}