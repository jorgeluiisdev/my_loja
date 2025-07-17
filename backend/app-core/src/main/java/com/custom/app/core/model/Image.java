package com.custom.app.core.model;

import java.util.UUID;

public class Image {
    private UUID id;
    private String pathImage;
    private ImageType type;
    private UUID productId;
    private UUID bannerId;
    private UUID userImageId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public ImageType getType() {
        return type;
    }

    public void setType(ImageType type) {
        this.type = type;
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