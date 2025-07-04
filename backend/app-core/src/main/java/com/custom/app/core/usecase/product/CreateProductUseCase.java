package com.custom.app.core.usecase.product;

import com.custom.app.core.model.Product;

public interface CreateProductUseCase {
    Product execute(Product product);
}