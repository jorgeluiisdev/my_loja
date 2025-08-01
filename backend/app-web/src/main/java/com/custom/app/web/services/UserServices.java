package com.custom.app.web.services;

import com.custom.app.core.model.User;
import com.custom.app.core.usecase.user.GetUserUseCase;
import com.custom.app.security.service.TokenService;
import com.custom.app.web.dto.request.LoginRequest;
import com.custom.app.web.dto.response.LoginResponse;
import com.custom.app.web.exceptions.InvalidCredentialsException;
import com.custom.app.web.mapper.UserResponseMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServices {

    private final TokenService tokenService;
    private final GetUserUseCase getUserUseCase;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServices(
            TokenService tokenService,
            GetUserUseCase getUserUseCase
    ) {
        this.tokenService = tokenService;
        this.getUserUseCase = getUserUseCase;
    }

    @Transactional
    public LoginResponse login(LoginRequest request, HttpServletResponse response) {
        Optional<User> user = this.getUserUseCase.findUserByLogin(request.getLogin());

        if (user.isPresent()) {

            User existingUser = user.get();

            if (!this.passwordEncoder.matches(request.getPassword(), existingUser.getPassword())) {
                throw new InvalidCredentialsException();
            }

            String accessToken = this.tokenService.generateToken(existingUser.getId(), existingUser.getLogin());
            String refreshToken = this.tokenService.generateRefreshToken(existingUser.getId(), existingUser.getLogin());

            this.tokenService.storeRefreshTokenHash(existingUser.getId(), refreshToken);
            this.tokenService.setRefreshTokenCookie(response, refreshToken);

            return UserResponseMapper.toLoginResponse(existingUser, accessToken);
        } else {
            return null;
        }
    }

    public ResponseEntity<Void> logout(String accessToken, HttpServletResponse response) {
        if (accessToken == null || !this.tokenService.isValid(accessToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UUID userId = this.tokenService.extractUserId(accessToken);
        this.tokenService.logout(userId);

        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .build();

        response.addHeader("Set-Cookie", deleteCookie.toString());

        return ResponseEntity.noContent().build();
    }
}