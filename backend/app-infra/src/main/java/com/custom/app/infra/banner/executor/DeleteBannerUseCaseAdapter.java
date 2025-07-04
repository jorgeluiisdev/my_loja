package com.custom.app.infra.banner.executor;

import com.custom.app.core.usecase.banner.DeleteBannerUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteBannerUseCaseAdapter implements DeleteBannerUseCase {

    @Override
    public void execute(UUID bannerId) {

    }
}