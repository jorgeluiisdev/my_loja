package com.custom.app.security.config;

import com.custom.app.security.filter.SecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;
    private final CorsConfig corsConfig;
    private final Environment env;

    public SecurityConfig(
            SecurityFilter securityFilter,
            CorsConfig corsConfig,
            Environment env
    ) {
        this.securityFilter = securityFilter;
        this.corsConfig = corsConfig;
        this.env = env;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
                    @Override
                    public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                        httpSecurityCorsConfigurer.configurationSource(corsConfig.corsConfigurationSource());
                    }
                }).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(new Customizer<SessionManagementConfigurer<HttpSecurity>>() {
                    @Override
                    public void customize(SessionManagementConfigurer<HttpSecurity> httpSecuritySessionManagementConfigurer) {
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                    }
                }).authorizeHttpRequests(new Customizer<AuthorizeHttpRequestsConfigurer<org.springframework.security.config.annotation.web.builders.HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>() {
                    @Override
                    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authMatchers) {
                        authMatchers.requestMatchers(HttpMethod.GET,
                                "/",
                                "/index.html",
                                "/login",
                                "/edit",
                                "/assets/**",
                                "/imgs/**",
                                "/vite.svg",
                                "/favicon.ico").permitAll();
                        authMatchers.requestMatchers(HttpMethod.POST, "/api/v1/login").permitAll();
                        authMatchers.requestMatchers(HttpMethod.DELETE, "/api/v1/logout");
                        authMatchers.requestMatchers(HttpMethod.GET, "/api/v1/products/categorized-products").permitAll();
                        authMatchers.requestMatchers(HttpMethod.POST, "/api/v1/products/create-product");
                        authMatchers.requestMatchers(HttpMethod.PUT, "/api/v1/products/update-product");
                        authMatchers.requestMatchers(HttpMethod.GET, "/api/v1/products/list-all").permitAll();
                        authMatchers.requestMatchers(HttpMethod.GET, "/api/v1/images/{uuid}").permitAll();

                        if (!List.of(env.getActiveProfiles()).contains("production")) {
                            authMatchers.requestMatchers(
                                    "/swagger-ui.html",
                                    "/swagger-ui/**",
                                    "/v3/api-docs",
                                    "/v3/api-docs/**",
                                    "/api/v1/docs",
                                    "/api/v1/docs/**"
                            ).permitAll();
                        }

                        authMatchers.anyRequest().authenticated();
                    }
                }).build();
    }
}