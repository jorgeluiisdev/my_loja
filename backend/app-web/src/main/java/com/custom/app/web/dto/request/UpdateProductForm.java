package com.custom.app.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Schema(description = "Detalhes do objeto composto de atualização de requisição de um Produto")
public class UpdateProductForm {

    @NotBlank
    @Schema(
            description = "JSON com os dados do produto e da categoria",
            example = "{\"id\":\"123e4567-e89b-12d3-a456-426614174000\",\"title\":\"Novo título\",\"description\":\"Nova descrição\",\"price\":122.3,\"sku\":\"ABC123\",\"categoryId\":\"987e6543-e21b-34d3-c456-123456789abc\"}"
    )
    private String productJson;

    @Schema(description = "Lista de novas imagens para substituir imagens existentes. A ordem deve corresponder com `currentImageIds`.")
    @Size(max = 4, message = "O número máximo de imagens para um produto foi atingido")
    private List<MultipartFile> images;

    @Schema(description = "Lista de IDs das imagens atuais que serão substituídas. Deve estar na mesma ordem de `images`.")
    @Size(max = 4, message = "O número máximo de imagens para um produto foi atingido")
    private List<UUID> currentImageIds;

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

    public List<UUID> getCurrentImageIds() {
        return currentImageIds;
    }

    public void setCurrentImageIds(List<UUID> currentImageIds) {
        this.currentImageIds = currentImageIds;
    }
}