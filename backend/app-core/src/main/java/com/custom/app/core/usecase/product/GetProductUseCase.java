package com.custom.app.core.usecase.product;

import com.custom.app.core.model.Product;

import java.util.Optional;
import java.util.UUID;

public interface GetProductUseCase {
    Optional<Product> execute(UUID productId);
}