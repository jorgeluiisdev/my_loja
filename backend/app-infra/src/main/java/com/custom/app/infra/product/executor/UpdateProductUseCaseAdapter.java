package com.custom.app.infra.product.executor;

import com.custom.app.core.model.Product;
import com.custom.app.core.usecase.product.UpdateProductUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateProductUseCaseAdapter implements UpdateProductUseCase {
    @Override
    public Product execute(UUID productId, Product product) {
        return null;
    }
}