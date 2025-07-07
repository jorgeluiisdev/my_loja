package com.custom.app.web.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

public class CategoryProductResponse {
    private String name;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;
    private String imageUrl;
    private String sku;

}