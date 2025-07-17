package com.custom.app.web.services;

import com.custom.app.core.model.Image;
import com.custom.app.core.model.ImageType;
import com.custom.app.infra.image.executor.CreateImageUseCaseAdapter;
import com.custom.app.infra.image.executor.DeleteImageUseCaseAdapter;
import com.custom.app.infra.image.executor.GetImageUseCaseAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageServices {

    /**
     * Core e infra não devem incluir spring-web como dependência, e infra não pode ter as injeções de-
     * valores do construtor abaixo, por tanto, o tratamento de imagens foi deixado em uma serviço separado-
     * dentro do módulo web, ainda que seja uma operação de infraestrutura.
     * Até a próxima refatoração…
     */

    private final CreateImageUseCaseAdapter createImageUseCaseAdapter;
    private final DeleteImageUseCaseAdapter deleteImageUseCaseAdapter;
    private final GetImageUseCaseAdapter getImageUseCaseAdapter;
    private final Map<String, String> EXTENSION_FOLDER_MAP;
    private final ResourceLoader resourceLoader;
    private final String defaultProfileImagePath;
    private final String defaultProductImagePath;
    private final String defaultBannerImagePath;
    private final String profileBasePath;
    private final String productBasePath;
    private final String bannerBasePath;

    @Autowired
    public ImageServices(
            CreateImageUseCaseAdapter createImageUseCaseAdapter,
            DeleteImageUseCaseAdapter deleteImageUseCaseAdapter,
            GetImageUseCaseAdapter getImageUseCaseAdapter,
            @Qualifier("webApplicationContext") ResourceLoader resourceLoader,
            @Value("${default.profile.image}") String defaultProfileImagePath,
            @Value("${default.product.image}") String defaultProductImagePath,
            @Value("${default.banner.image}") String defaultBannerImagePath,
            @Value("${upload.image.profile.base-path}") String profileBasePath,
            @Value("${upload.image.product.base-path}") String productBasePath,
            @Value("${upload.image.banner.base-path}") String bannerBasePath
    ) {
        this.createImageUseCaseAdapter = createImageUseCaseAdapter;
        this.deleteImageUseCaseAdapter = deleteImageUseCaseAdapter;
        this.getImageUseCaseAdapter = getImageUseCaseAdapter;
        this.defaultProfileImagePath = defaultProfileImagePath;
        this.defaultProductImagePath = defaultProductImagePath;
        this.defaultBannerImagePath = defaultBannerImagePath;
        this.profileBasePath = profileBasePath;
        this.productBasePath = productBasePath;
        this.bannerBasePath = bannerBasePath;
        this.resourceLoader = resourceLoader;
        this.EXTENSION_FOLDER_MAP = Map.of(
                ".jpeg", "JPG/",
                ".jpg",  "JPG/",
                ".jpe",  "JPG/",
                ".png",  "PNG/",
                ".svg",  "Svg/",
                ".webp", "Webp/"
        );
    }

    public Map<UUID, String> prepareImagePaths(ImageType imageType, List<MultipartFile> files) {
        return files.stream().map(file -> {
            UUID uuid = UUID.randomUUID();
            String originalName = file.getOriginalFilename();

            if (originalName == null) {
                throw new IllegalArgumentException("Nome do arquivo está nulo.");
            }

            String extension = this.extractValidExtension(originalName);
            String fileName = this.generateFileName(uuid, extension);
            String fullPath = this.getFullPath(imageType, fileName, extension);

            return Map.entry(uuid, fullPath);
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void writeImages(List<MultipartFile> files, Map<UUID, String> uuidToPath) {
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            UUID uuid = (UUID) uuidToPath.keySet().toArray()[i];
            String path = uuidToPath.get(uuid);
            try {
                Path fullPath = Paths.get(path);
                Files.createDirectories(fullPath.getParent());
                file.transferTo(fullPath);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao salvar imagem", e);
            }
        }
    }

    public void rollbackImages(Map<UUID, String> uuidToPath) {
        for (String pathStr : uuidToPath.values()) {
            try {
                Files.deleteIfExists(Paths.get(pathStr));
            } catch (IOException ignored) {
            }
        }
    }

    public String getFullPath(ImageType imageType, String fileName, String extension) {
        String destinationFolder = this.EXTENSION_FOLDER_MAP.get(extension);
        String basePath = this.resolveBasePathByType(imageType);
        return Paths.get(basePath, destinationFolder, fileName).toString();
    }

    private String resolveBasePathByType(ImageType type) {
        return switch (type) {
            case ImageType.PROFILE -> this.profileBasePath;
            case ImageType.PRODUCT -> this.productBasePath;
            case ImageType.BANNER  -> this.bannerBasePath;
            default -> throw new IllegalArgumentException("Tipo de imagem não suportado ou desconhecido.");
        };
    }

    public String extractValidExtension(String filename) {
        return this.EXTENSION_FOLDER_MAP.keySet().stream()
                .filter(filename.toLowerCase()::endsWith)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Formato de imagem não suportado"));
    }

    public String generateFileName(UUID uuid, String extension) {
        return String.format(
                "img_%s_%s%s",
                uuid,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss")),
                extension
        );
    }

    public void saveImage(List<Image> images) {
        this.createImageUseCaseAdapter.createImage(images);
    }

    public record ImageResource(Resource resource, MediaType mediaType) {}

    public ImageResource loadImage(UUID imageId) {
        Optional<Image> imageOptional = this.getImageUseCaseAdapter.findById(imageId);
        if (imageOptional.isEmpty()) {
            throw new ResourceAccessException("Erro ao localizar imagem.");
            //retornar ou imagem de erro, ou só o erro.
        }

        Image image = imageOptional.get();
        Resource resource = this.getFileAsResource(image.getPathImage());

        if (resource == null) {
            resource = switch (image.getType()) {
                case ImageType.BANNER -> this.resourceLoader.getResource(this.defaultBannerImagePath);
                case ImageType.PRODUCT -> this.resourceLoader.getResource(this.defaultProductImagePath);
                case ImageType.PROFILE -> this.resourceLoader.getResource(this.defaultProfileImagePath);
            };
        }

        MediaType mediaType = this.detectMediaType(resource);
        return new ImageResource(resource, mediaType);
    }

    public Resource getFileAsResource(String path) {
        try {
            Path filePath = Paths.get(path);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
        } catch (MalformedURLException e) {
            throw new ResourceAccessException("URL malformada ao tentar acessar o arquivo de imagem.", e);
        }
        return null;
    }

    public MediaType detectMediaType(Resource resource) {
        try {
            Path path = resource.getFile().toPath();
            String contentType = Files.probeContentType(path);
            return contentType != null ? MediaType.parseMediaType(contentType) : MediaType.APPLICATION_OCTET_STREAM;
        } catch (IOException e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    public void deleteImages(List<Image> images) {
        try {
            for (Image img : images) {
                Files.deleteIfExists(Paths.get(img.getPathImage()));
            }
            for (Image img : images) {
                this.deleteImageUseCaseAdapter.deleteImage(img.getId());
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao deletar imagens físicas", e);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar registros das imagens", e);
        }
    }
}