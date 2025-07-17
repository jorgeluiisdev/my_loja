package com.custom.app.infra.product.executor;

import com.custom.app.core.model.Product;
import com.custom.app.core.port.ProductRepository;
import com.custom.app.core.usecase.product.UpdateProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateProductUseCaseAdapter implements UpdateProductUseCase {

    private final ProductRepository productRepository;

    @Autowired
    public UpdateProductUseCaseAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product updateProduct(UUID productId, Product product) {
        return this.productRepository.update(productId, product);
    }
}