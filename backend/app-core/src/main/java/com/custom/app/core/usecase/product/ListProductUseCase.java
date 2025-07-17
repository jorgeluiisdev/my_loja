package com.custom.app.core.usecase.product;

import com.custom.app.core.model.Product;

import java.util.List;
import java.util.Map;

public interface ListProductUseCase {
    List<Product> listAllProducts();
    Map<String, List<Product>> listProductsByCategory();
}