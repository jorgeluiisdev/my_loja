package com.custom.app.core.usecase.product;

import com.custom.app.core.model.Product;

import java.util.List;

public interface ListProductUseCase {
    List<Product> execute();
}