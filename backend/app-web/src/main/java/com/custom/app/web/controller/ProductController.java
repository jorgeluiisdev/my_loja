package com.custom.app.web.controller;

import com.custom.app.web.controller.versioning.ApiPaths;
import com.custom.app.web.dto.request.CreateProductForm;
import com.custom.app.web.dto.request.CreateProductRequest;
import com.custom.app.web.dto.request.UpdateProductForm;
import com.custom.app.web.dto.request.UpdateProductRequest;
import com.custom.app.web.dto.response.CategoryProductsResponse;
import com.custom.app.web.dto.response.ProductResponse;
import com.custom.app.web.services.ProductServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(name = "Endpoint de Produtos", description = "Rotas que dizem respeito a operações com Produtos")
@RestController
@RequestMapping(ApiPaths.BASE_API_V1 + "/products")
public class ProductController {

    private final ProductServices services;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductController(
            ProductServices services,
            ObjectMapper objectMapper
    ) {
        this.services = services;
        this.objectMapper = objectMapper;
    }

    @Operation(
            summary = "Cria um novo produto com imagens",
            description = "Envia os dados do produto no campo 'productJson' (formato JSON), e uma ou mais imagens no campo 'images'.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping(value = "/create-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> createProduct(@ModelAttribute @Valid CreateProductForm form) {
        CreateProductRequest request;
        try {
            request = this.objectMapper.readValue(form.getProductJson(), CreateProductRequest.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JSON inválido.", e);
        }

        ProductResponse response = this.services.createProduct(request, form.getImages());
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Atualiza um produto anteriormente criado",
            description = "Envia dos dados do produto alvo e retorna o produto atualizado.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PutMapping(value = "/update-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateProduct(@ModelAttribute @Valid UpdateProductForm form) {
        UpdateProductRequest request;
        try {
            request = this.objectMapper.readValue(form.getProductJson(), UpdateProductRequest.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JSON inválido.", e);
        }

        ProductResponse response = this.services.updateProduct(request, form.getImages(), form.getCurrentImageIds());

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Retorna uma lista de todos os produtos",
            description = "Não recebe nada como parâmetro e retorna uma lista de produtos"
    )
    @GetMapping("/list-all")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(this.services.getAllProducts());
    }

    @Operation(
            summary = "Retorna uma lista de produtos agrupados por categoria",
            description = "Não recebe nada como parâmetro e retorna uma uma Lista de Produtos agrupados por categoria"
//            security = @SecurityRequirement(name = "bearerAuth") // Não precisa de autenticação
    )
    @GetMapping("/categorized-products")
    public ResponseEntity<List<CategoryProductsResponse>> getAllCategorizedProducts() {
        List<CategoryProductsResponse> responses = this.services.getCategoryProducts();
        return ResponseEntity.ok(responses);
    }
}