package com.custom.app.web.controller;

import com.custom.app.web.controller.versioning.ApiPaths;
import com.custom.app.web.services.ImageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(ApiPaths.BASE_API_V1)
public class ImageController {

    private final ImageServices imageServices;

    @Autowired
    public ImageController(ImageServices imageServices) {
        this.imageServices = imageServices;
    }

    @GetMapping("/images/{uuid}")
    public ResponseEntity<Resource> getImage(@PathVariable UUID uuid) {
        ImageServices.ImageResource imageResource = this.imageServices.loadImage(uuid);

        return ResponseEntity.ok()
                .contentType(imageResource.mediaType())
                .body(imageResource.resource());
    }
}