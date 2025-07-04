package com.custom.app.core.usecase.product;

import java.util.UUID;

public interface DeleteProductUseCase {
    void execute(UUID productId);
}