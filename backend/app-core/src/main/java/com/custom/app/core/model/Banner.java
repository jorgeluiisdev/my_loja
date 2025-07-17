package com.custom.app.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Banner {
    private UUID id;
    private List<Image> images = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}