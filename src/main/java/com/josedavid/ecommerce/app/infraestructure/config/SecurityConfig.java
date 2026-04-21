package com.josedavid.ecommerce.app.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth

                .requestMatchers(
                        "/auth/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll()

                .requestMatchers(HttpMethod.GET, "/products/**")
                    .hasAnyRole("USER", "ADMIN")

                .requestMatchers(HttpMethod.POST, "/products")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PUT, "/products/**")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.DELETE, "/products/**")
                    .hasRole("ADMIN")

                .requestMatchers("/cart/**")
                    .hasAnyRole("USER","ADMIN")

                .requestMatchers(HttpMethod.POST, "/orders/checkout")
                    .hasRole("USER")

                .requestMatchers(HttpMethod.GET, "/orders/my-orders")
                    .hasRole("USER")

                .requestMatchers(HttpMethod.GET, "/orders")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PUT, "/orders/*/status")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/me").authenticated()
                .requestMatchers(HttpMethod.PUT, "/me").authenticated()

                .anyRequest().authenticated()
        );

        http.addFilterBefore(jwtFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}