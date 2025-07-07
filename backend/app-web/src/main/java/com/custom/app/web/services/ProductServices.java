package com.custom.app.web.services;

import com.custom.app.core.model.Product;
import com.custom.app.core.usecase.product.ListProductUseCase;
import com.custom.app.web.dto.response.CategoryProductsResponse;
import com.custom.app.web.dto.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServices {

    private final ListProductUseCase listProductUseCase;

    @Autowired
    public ProductServices(ListProductUseCase listProductUseCase) {
        this.listProductUseCase = listProductUseCase;
    }

    public List<CategoryProductsResponse> getCategoryProducts() {
        Map<String, List<Product>> productsByCategory = this.listProductUseCase.execute();

        return productsByCategory.entrySet().stream()
                .map(entry -> {
                    CategoryProductsResponse categoryDto = new CategoryProductsResponse();
                    categoryDto.setCategoryName(entry.getKey());

                    // Limita a 6 produtos por categoria
                    List<ProductResponse> productResponses = entry.getValue().stream()
                            .limit(6)
                            .map(product -> {
                                ProductResponse dto = new ProductResponse();
                                dto.setName(product.getTitle());
                                dto.setDescription(product.getDescription());
                                dto.setPrice(product.getPrice());
                                dto.setImageUrl(product.getImageUrl());
                                dto.setSku(product.getSku());
                                return dto;
                            })
                            .collect(Collectors.toList());

                    categoryDto.setProducts(productResponses);
                    return categoryDto;
                })
                .collect(Collectors.toList());
    }

}