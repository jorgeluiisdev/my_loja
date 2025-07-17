package com.custom.app.persistence.mapepr;

import com.custom.app.core.model.Image;
import com.custom.app.persistence.entity.ImageEntity;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    public ImageEntity toEntity(Image image) {
        ImageEntity imageEntity = new ImageEntity();

        if (image.getId() != null) {
            imageEntity.setId(image.getId());
        }
        imageEntity.setType(image.getType());
        imageEntity.setPathImage(image.getPathImage());
        imageEntity.setProductId(image.getProductId());
        imageEntity.setBannerId(image.getBannerId());
        imageEntity.setUserImageId(image.getUserImageId());

        return imageEntity;
    }

    public Image toDomain(ImageEntity imageEntity) {
        Image image = new Image();

        image.setId(imageEntity.getId());
        image.setType(imageEntity.getType());
        image.setPathImage(imageEntity.getPathImage());
        image.setProductId(imageEntity.getProductId());
        image.setBannerId(imageEntity.getBannerId());
        image.setUserImageId(imageEntity.getUserImageId());

        return image;
    }
}