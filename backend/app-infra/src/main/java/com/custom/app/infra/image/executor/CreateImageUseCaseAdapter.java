package com.custom.app.infra.image.executor;

import com.custom.app.core.model.Image;
import com.custom.app.core.usecase.image.CreateImageUseCase;
import com.custom.app.persistence.adapter.ImageRepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateImageUseCaseAdapter implements CreateImageUseCase {

    private final ImageRepositoryAdapter imageRepositoryAdapter;

    @Autowired
    public CreateImageUseCaseAdapter(
            ImageRepositoryAdapter imageRepositoryAdapter
    ) {
        this.imageRepositoryAdapter = imageRepositoryAdapter;
    }

    @Override
    public List<Image> createImage(List<Image> images) {
        return this.imageRepositoryAdapter.save(images);
    }
}