package com.custom.app.infra.product.executor;

import com.custom.app.core.model.Product;
import com.custom.app.core.port.ProductRepository;
import com.custom.app.core.usecase.product.GetProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetProductUseCaseAdapter implements GetProductUseCase {

    private final ProductRepository productRepository;

    @Autowired
    public GetProductUseCaseAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        return this.productRepository.findById(productId);
    }
}