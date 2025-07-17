package com.custom.app.persistence.entity;

import com.custom.app.core.model.ImageType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class ImageEntity {
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ImageType type;

    @Column(nullable = false)
    private String pathImage;

    @Column(name = "product_id", insertable = false, updatable = false)
    private UUID productId;

    @Column(name = "banner_id", insertable = false, updatable = false)
    private UUID bannerId;

    @Column(name = "user_image_id", insertable = false, updatable = false)
    private UUID userImageId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ImageType getType() {
        return type;
    }

    public void setType(ImageType type) {
        this.type = type;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public UUID getBannerId() {
        return bannerId;
    }

    public void setBannerId(UUID bannerId) {
        this.bannerId = bannerId;
    }

    public UUID getUserImageId() {
        return userImageId;
    }

    public void setUserImageId(UUID userImageId) {
        this.userImageId = userImageId;
    }
}