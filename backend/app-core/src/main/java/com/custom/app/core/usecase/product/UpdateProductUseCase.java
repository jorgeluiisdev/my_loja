package com.custom.app.core.usecase.product;

import com.custom.app.core.model.Product;

import java.util.UUID;

public interface UpdateProductUseCase {
    Product updateProduct(UUID productId, Product product);
}