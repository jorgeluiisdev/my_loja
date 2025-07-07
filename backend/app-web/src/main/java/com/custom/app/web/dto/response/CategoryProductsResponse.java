package com.custom.app.web.dto.response;

import java.util.List;

public class CategoryProductsResponse {
    private String categoryName;
    private List<ProductResponse> products;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }
}