package com.custom.app.web.controller;

import com.custom.app.security.filter.SecurityFilter;
import com.custom.app.web.controller.versioning.ApiPaths;
import com.custom.app.web.dto.request.LoginRequest;
import com.custom.app.web.dto.response.LoginResponse;
import com.custom.app.web.services.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticação", description = "Endpoints de login e SSE")
@RestController
@RequestMapping(ApiPaths.BASE_API_V1)
public class AuthController {

    private final UserServices userServices;

    @Autowired
    public AuthController(UserServices userServices) {
        this.userServices = userServices;
    }

    @Operation(
            summary = "Faz login e retorna token JWT",
            description = "Recebe as credenciais do usuário (login e senha) e retorna um token JWT para autenticação."
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest requestDto,
            HttpServletResponse response
    ) {
        LoginResponse responseDto = this.userServices.login(requestDto, response);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Logout do usuário", description = "Revoga o token de refresh e encerra a sessão.")
    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = SecurityFilter.recoverToken(request);
        return this.userServices.logout(accessToken, response);
    }
}