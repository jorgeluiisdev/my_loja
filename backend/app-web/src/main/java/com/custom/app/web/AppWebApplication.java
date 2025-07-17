package com.custom.app.web;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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

    public static void main(String[] args) {
        SpringApplication.run(AppWebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}