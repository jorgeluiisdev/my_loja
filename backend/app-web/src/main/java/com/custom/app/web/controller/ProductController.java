package com.custom.app.web.controller;

import com.custom.app.core.model.Product;
import com.custom.app.core.usecase.product.ListProductUseCase;
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

    private final ListProductUseCase listProductUseCase;

    @Autowired
    public ProductController(
            ListProductUseCase listProductUseCase
    ) {
        this.listProductUseCase = listProductUseCase;
    }

    @Operation(
            summary = "Retorna uma lista de produtos",
            description = "Não recebe nada e retorna uma uma Lista de Produtos"
//            security = @SecurityRequirement(name = "bearerAuth") // Não precisa de autenticação
    )
    @GetMapping("/list-all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = this.listProductUseCase.execute();
        return ResponseEntity.ok(products); // TODO | Falta transformar para o dto de resposta depois…
    }

    // Acesse: http://localhost:8080/swagger-ui/index.html
}