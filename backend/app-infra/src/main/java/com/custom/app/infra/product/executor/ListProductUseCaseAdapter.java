package com.custom.app.infra.product.executor;

import com.custom.app.core.model.Product;
import com.custom.app.core.port.ProductRepository;
import com.custom.app.core.usecase.product.ListProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListProductUseCaseAdapter implements ListProductUseCase {

    private final ProductRepository productRepository;

    @Autowired
    public ListProductUseCaseAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> execute() {
        return this.productRepository.findAll();
    }
}