package com.custom.app.web;

import com.custom.app.core.model.Image;
import com.custom.app.core.model.ImageType;
import com.custom.app.core.model.User;
import com.custom.app.core.model.UserRole;
import com.custom.app.core.usecase.image.CreateImageUseCase;
import com.custom.app.core.usecase.user.CreateUserUseCase;
import com.custom.app.core.usecase.user.GetUserUseCase;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootApplication(scanBasePackages = "com.custom")
@EnableJpaRepositories(basePackages = "com.custom.app.persistence.repository")
@EntityScan(basePackages = "com.custom.app.persistence.entity")
@OpenAPIDefinition(
        info = @Info(
        title = "Product Service API",
        version = "v1",
        description = "API for managing products"
        )
)
public class AppWebApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppWebApplication.class);

    private final GetUserUseCase getUserUseCase;
    private final CreateImageUseCase createImageUseCase;
    private final CreateUserUseCase createUserUseCase;

    @Autowired
    public AppWebApplication(
            GetUserUseCase getUserUseCase,
            CreateImageUseCase createImageUseCase,
            CreateUserUseCase createUserUseCase
    ) {
        this.getUserUseCase = getUserUseCase;
        this.createImageUseCase = createImageUseCase;
        this.createUserUseCase = createUserUseCase;
    }

    public static void main(String[] args) {
        SpringApplication.run(AppWebApplication.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        if (this.getUserUseCase.findUserByLogin("admin").isPresent()) {
            logger.info("Admin já criado");
        } else {
            Image img = new Image();
            UUID imageId = UUID.randomUUID();
            img.setId(imageId);
            img.setPathImage("/test");
            img.setType(ImageType.PROFILE);

            Image savedImage = this.createImageUseCase.createImage(img);

            User newUser = new User();
            newUser.setLogin("admin");
            newUser.setFirstName("admin");
            newUser.setLastName("developer");
            newUser.setPassword(new BCryptPasswordEncoder().encode("admin"));
            newUser.setRole(UserRole.DEV);
            newUser.setEmail("admin@developer.com");
            newUser.setUserImageId(savedImage.getId());

            this.createUserUseCase.createUser(newUser);
        }
    }
}