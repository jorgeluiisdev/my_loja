package com.custom.app.persistence.mapepr;

import com.custom.app.core.model.Banner;
import com.custom.app.persistence.entity.BannerEntity;
import org.springframework.stereotype.Component;

@Component
public class BannerMapper {

    public BannerEntity toEntity(Banner banner) {
        BannerEntity bannerEntity = new BannerEntity();

        bannerEntity.setImageUrl(bannerEntity.getImageUrl());

        return bannerEntity;
    }

    public Banner toModel(BannerEntity bannerEntity) {
        Banner banner = new Banner();

        banner.setImageUrl(bannerEntity.getImageUrl());

        return banner;
    }
}