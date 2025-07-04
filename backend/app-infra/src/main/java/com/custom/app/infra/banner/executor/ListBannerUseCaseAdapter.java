package com.custom.app.infra.banner.executor;

import com.custom.app.core.model.Banner;
import com.custom.app.core.usecase.banner.ListBannerUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListBannerUseCaseAdapter implements ListBannerUseCase {
    @Override
    public List<Banner> execute() {
        return List.of();
    }
}