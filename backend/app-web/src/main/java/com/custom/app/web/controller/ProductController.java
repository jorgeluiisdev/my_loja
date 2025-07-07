package com.custom.app.web.controller;

import com.custom.app.web.dto.response.CategoryProductsResponse;
import com.custom.app.web.services.ProductServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Endpoint de Produtos", description = "Rotas que dizem respeito a operações com Produtos")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductServices services;

    @Autowired
    public ProductController(
            ProductServices services
    ) {
        this.services = services;
    }

    @Operation(
            summary = "Retorna uma lista de produtos agrupador por categoria",
            description = "Não recebe nada como parâmetro e retorna uma uma Lista de Produtos agrupados por categoria"
//            security = @SecurityRequirement(name = "bearerAuth") // Não precisa de autenticação
    )
    @GetMapping("/list-all")
    public ResponseEntity<List<CategoryProductsResponse>> getAllProducts() {
        List<CategoryProductsResponse> responses = this.services.getCategoryProducts();
        return ResponseEntity.ok(responses);
    }

    // Acesse: http://localhost:8080/swagger-ui/index.html
}