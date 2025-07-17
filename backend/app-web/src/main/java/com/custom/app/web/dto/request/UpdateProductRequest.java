package com.custom.app.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Detalhes do objeto de atualização dos dados de um produto")
public class UpdateProductRequest {

    @NotEmpty(message = "O identificador do produto não pode estar vazio")
    private UUID id;
    private String title;
    private String description;
    private BigDecimal price;
    private String sku;
    @NotEmpty(message = "O produto precisa de uma categoria obrigatoriamente")
    private UUID categoryId;
    private String categoryName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}