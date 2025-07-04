package com.custom.app.infra.product.executor;

import com.custom.app.core.usecase.product.DeleteProductUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteProductUseCaseAdapter implements DeleteProductUseCase {

    @Override
    public void execute(UUID userId) {

    }
}