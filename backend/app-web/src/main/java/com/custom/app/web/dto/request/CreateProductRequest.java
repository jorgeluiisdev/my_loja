package com.custom.app.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

@Schema(description = "Detalhes do objeto de requisição de Produtos")
public class CreateProductRequest {
    @NotEmpty(message = "O título do produto não pode ser nulo")
    private String title;
    private String description;
    @NotEmpty(message = "O preço do produto não pode ser nulo")
    private BigDecimal price;
    private String sku;
    @NotEmpty(message = "O produto precisa de uma categoria obrigatoriamente")
    private String categoryName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}