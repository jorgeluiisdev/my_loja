package com.custom.app.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.custom")
@EnableJpaRepositories(basePackages = "com.custom.app.persistence.repository")
@EntityScan(basePackages = "com.custom.app.persistence.entity")
public class AppWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppWebApplication.class, args);
    }

}