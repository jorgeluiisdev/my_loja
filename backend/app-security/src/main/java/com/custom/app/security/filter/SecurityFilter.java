package com.custom.app.security.filter;

import com.custom.app.core.model.User;
import com.custom.app.core.usecase.user.GetUserUseCase;
import com.custom.app.security.exceptions.UnauthorizedAccessException;
import com.custom.app.security.model.UserDetailsImpl;
import com.custom.app.security.service.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final GetUserUseCase getUserUseCase;

    public SecurityFilter(
            TokenService tokenService,
            GetUserUseCase getUserUseCase
    ) {
        this.tokenService = tokenService;
        this.getUserUseCase = getUserUseCase;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) {
        try {
            String accessToken = recoverToken(request);
            String newAccessToken = this.processTokenRenewal(request, response, accessToken);
            this.setSecurityContext(newAccessToken != null ? newAccessToken : accessToken);
            filterChain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    private String processTokenRenewal(HttpServletRequest request, HttpServletResponse response, String token) {
        if (token == null) return null;
        try {
            if (this.tokenService.isTokenExpiringSoon(token) || !this.tokenService.isValid(token)) {
                String refreshToken = this.recoverRefreshToken(request);
                String newToken = this.tokenService.refreshAccessToken(refreshToken);
                response.setHeader("X-Access-Token", newToken);
                return newToken;
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return null;
    }

    private void setSecurityContext(String token) {
        if (token != null && this.tokenService.isValid(token)) {
            UUID userId = this.tokenService.extractUserId(token);
            User user = this.getUserUseCase.findUserById(userId)
                    .orElseThrow(UnauthorizedAccessException::new);
            UserDetailsImpl userDetails = new UserDetailsImpl(user);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private String recoverRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static String getAuthenticatedLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetailsImpl userDetails) {
            return userDetails.getLogin();
        }

        return null;
    }

    public static String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return authHeader == null ? null : authHeader.replace("Bearer ", "");
    }
}