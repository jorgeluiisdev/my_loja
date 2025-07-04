package com.custom.app.core.port;

import com.custom.app.core.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save(Product product);
    Product update(Product product);
    void delete(UUID existingProductId);
    List<Product> findAll();
    Optional<Product> findById(UUID productId);
    Optional<Product> findBySku(String productSku);
    Optional<Product> findByName(String productName);
}