package com.custom.app.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Schema(description = "Detalhes do objeto composto de requisição de Produtos")
public class CreateProductForm {

    @NotBlank
    @Schema(
            description = "JSON com os dados do produto",
            example = "{\"title\":\"Calça de verão\",\"description\":\"Boa para dias quentes\",\"price\":122.3,\"sku\":\"ABC123\",\"categoryName\":\"Roupas Masculinas\"}"
    )
    private String productJson;

    @Schema(description = "Lista de imagens do produto")
    @Size(max = 4, message = "O número máximo de imagens para um produto foi atingido")
    private List<MultipartFile> images;

    public String getProductJson() {
        return productJson;
    }

    public void setProductJson(String productJson) {
        this.productJson = productJson;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}