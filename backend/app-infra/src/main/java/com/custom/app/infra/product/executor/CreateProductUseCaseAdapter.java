package com.custom.app.infra.product.executor;

import com.custom.app.core.model.Product;
import com.custom.app.core.port.ProductRepository;
import com.custom.app.core.usecase.product.CreateProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateProductUseCaseAdapter implements CreateProductUseCase {

    private final ProductRepository productRepository;

    @Autowired
    public CreateProductUseCaseAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product execute(Product product) {
        return this.productRepository.save(product);
    }
}