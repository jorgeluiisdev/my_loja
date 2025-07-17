package com.custom.app.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.custom.app.core.model.User;
import com.custom.app.core.usecase.user.GetUserUseCase;
import com.custom.app.core.usecase.user.UpdateUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class TokenService {
    private final Algorithm algorithm;
    private final Duration accessTokenDuration;
    private final Duration refreshTokenDuration = Duration.ofDays(7);
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final ConcurrentMap<UUID, String> activeRefreshTokens = new ConcurrentHashMap<>();

    @Autowired
    public TokenService(
            // Valor de env tem que ser mudado de lugar
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration}") Duration accessTokenDuration,
            UpdateUserUseCase updateUserUseCase,
            GetUserUseCase getUserUseCase
    ) {
        this.algorithm = Algorithm.HMAC512(secret);
        this.accessTokenDuration = accessTokenDuration;
        this.updateUserUseCase = updateUserUseCase;
        this.getUserUseCase = getUserUseCase;
    }

    private String buildJWT(UUID userId, String login, Duration duration) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + duration.toMillis());
        return JWT.create()
                .withSubject(userId.toString())
                .withClaim("login", login)
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .sign(this.algorithm);
    }

    public String generateToken(UUID userId, String login) {
        return this.buildJWT(userId, login, this.accessTokenDuration);
    }

    private void revokePreviousTokens(UUID userId) {
        this.activeRefreshTokens.remove(userId);
        Optional<User> user = this.getUserUseCase.execute(userId);
        if (user.isPresent()) {
            user.get().setRefreshTokenHash("revoked");
            this.updateUserUseCase.execute(user.get());
        }
    }

    public void logout(UUID userId) {
        this.revokePreviousTokens(userId);
    }


}