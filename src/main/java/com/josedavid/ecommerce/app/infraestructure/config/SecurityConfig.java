package com.josedavid.ecommerce.app.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    private static final String[] PUBLIC_ENDPOINTS = {
            "/auth/**",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    private static final String[] USER_ENDPOINTS = {
            "/cart/**",
            "/addresses/**",
            "/me"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth

                // Públicos
                .requestMatchers(PUBLIC_ENDPOINTS).permitAll()

                // Productos
                .requestMatchers(HttpMethod.GET, "/products/**")
                .hasAnyRole("USER", "ADMIN")

                .requestMatchers(HttpMethod.POST, "/products")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PUT, "/products/**")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.DELETE, "/products/**")
                .hasRole("ADMIN")

                // Usuario autenticado
                .requestMatchers(USER_ENDPOINTS)
                .authenticated()

                // Pedidos usuario
                .requestMatchers(HttpMethod.POST, "/orders/checkout")
                .hasRole("USER")

                .requestMatchers(HttpMethod.GET, "/orders/my-orders")
                .hasRole("USER")

                .requestMatchers(HttpMethod.GET, "/orders/*")
                .hasRole("USER")

                .requestMatchers(HttpMethod.PUT, "/orders/*/cancel")
                .hasRole("USER")

                // Pagos
                .requestMatchers(HttpMethod.POST, "/payments/**")
                .hasRole("USER")

                // Admin pedidos
                .requestMatchers(HttpMethod.GET, "/orders")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PUT, "/orders/*/status")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/orders/admin/**")
                .hasRole("ADMIN")

                .anyRequest().authenticated()
        );

        http.addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}