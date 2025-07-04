package com.custom.app.infra.product.executor;

import com.custom.app.core.model.Product;
import com.custom.app.core.usecase.product.GetProductUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetProductUseCaseAdapter implements GetProductUseCase {

    @Override
    public Optional<Product> execute(UUID productId) {
        return Optional.empty();
    }
}