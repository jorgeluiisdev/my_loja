package com.custom.app.persistence.mapepr;

import com.custom.app.core.model.Banner;
import com.custom.app.core.model.Image;
import com.custom.app.persistence.entity.BannerEntity;
import com.custom.app.persistence.entity.ImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BannerMapper {

    private final ImageMapper imageMapper;

    @Autowired
    public BannerMapper(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }

    public BannerEntity toEntity(Banner banner) {
        BannerEntity bannerEntity = new BannerEntity();

        if (banner.getId() != null) {
            bannerEntity.setId(banner.getId());
        }

        List<ImageEntity> images = banner.getImages().stream()
                .map(this.imageMapper::toEntity)
                .collect(Collectors.toList());

        bannerEntity.setImages(images);

        return bannerEntity;
    }

    public Banner toDomain(BannerEntity bannerEntity) {
        Banner banner = new Banner();

        banner.setId(bannerEntity.getId());

        List<Image> images = bannerEntity.getImages().stream()
                .map(this.imageMapper::toDomain)
                .collect(Collectors.toList());

        banner.setImages(images);

        return banner;
    }
}