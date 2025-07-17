package com.custom.app.web.dto.response;

import com.custom.app.core.model.Image;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Schema(description = "Detalhes do objeto de resposta de Produtos")
public class ProductResponse {
    private String title;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;
    List<Image> imagens;
    private String sku;

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

    public List<Image> getImagens() {
        return imagens;
    }

    public void setImagens(List<Image> imagens) {
        this.imagens = imagens;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}