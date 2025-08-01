package com.custom.app.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.custom.app.core.model.User;
import com.custom.app.core.usecase.user.GetUserUseCase;
import com.custom.app.core.usecase.user.UpdateUserUseCase;
import com.custom.app.security.exceptions.InvalidTokenException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public String generateRefreshToken(UUID userId, String login) {
        String currentToken = this.activeRefreshTokens.get(userId);
        if (currentToken != null) {
            DecodedJWT jwt = JWT.decode(currentToken);
            if (jwt.getExpiresAt().after(new Date())) {
                throw new InvalidTokenException("Já existe uma sessão ativa para esse usuário.");
            }
        }

        this.revokePreviousTokens(userId);
        String refreshToken = this.buildJWT(userId, login, this.refreshTokenDuration);
        this.activeRefreshTokens.put(userId, refreshToken);
        return refreshToken;
    }

    private void revokePreviousTokens(UUID userId) {
        this.activeRefreshTokens.remove(userId);
        Optional<User> user = this.getUserUseCase.findUserById(userId);
        if (user.isPresent()) {
            user.get().setRefreshTokenHash("revoked");
            this.updateUserUseCase.updateUser(user.get().getId(), user.get());
        }
    }

    public void logout(UUID userId) {
        this.revokePreviousTokens(userId);
    }

    public void storeRefreshTokenHash(UUID userId, String refreshToken) {
        try {
            String tokenSha256 = this.generateSHA256(refreshToken);
            String hashedToken = BCrypt.hashpw(tokenSha256, BCrypt.gensalt());
            User user = this.getUserUseCase.findUserById(userId).orElseThrow();
            user.setRefreshTokenHash(hashedToken);
            this.updateUserUseCase.updateUser(user.getId(), user);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao processar token", e);
        }
    }

    private String generateSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return this.bytesToHex(hash);
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(this.refreshTokenDuration.toSeconds())
                .sameSite("Strict")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    public boolean isValid(String token) {
        try {
            this.buildVerifier().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenExpiringSoon(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt().getTime() - System.currentTimeMillis() < 300_000;
    }

    public String extractLogin(String token) {
        return this.buildVerifier().verify(token).getClaim("login").asString();
    }

    public UUID extractUserId(String token) {
        return UUID.fromString(this.buildVerifier().verify(token).getSubject());
    }

    private JWTVerifier buildVerifier() {
        return JWT.require(this.algorithm).build();
    }

    public String refreshAccessToken(String refreshToken) {
        try {
            DecodedJWT jwt = JWT.decode(refreshToken);
            UUID userId = UUID.fromString(jwt.getSubject());
            String activeToken = this.activeRefreshTokens.get(userId);
            if (activeToken == null || !activeToken.equals(refreshToken)) {
                throw new InvalidTokenException();
            }
            User user = this.getUserUseCase.findUserById(userId)
                    .orElseThrow(InvalidTokenException::new);
            this.validateTokenMatch(refreshToken, user);
            return this.generateToken(userId, user.getLogin());
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    private void validateTokenMatch(String refreshToken, User user) {
        try {
            String tokenSha256 = this.generateSHA256(refreshToken);
            if (user.getRefreshTokenHash() == null ||
                    !BCrypt.checkpw(tokenSha256, user.getRefreshTokenHash())) {
                throw new InvalidTokenException();
            }
        } catch (NoSuchAlgorithmException e) {
            throw new InvalidTokenException("Erro ao validar token", e);
        }
    }
}