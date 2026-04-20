package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.application.service.JwtService;
import com.josedavid.ecommerce.app.domain.entity.RefreshToken;
import com.josedavid.ecommerce.app.domain.entity.User;
import com.josedavid.ecommerce.app.infraestructure.adapters.input.dto.AuthResponse;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.RefreshTokenRepository;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RefreshTokenUseCase {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository repository;

    public RefreshTokenUseCase(JwtService jwtService, UserRepository userRepository, RefreshTokenRepository repository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.repository = repository;
    }

    public AuthResponse execute(String refreshToken) {

        // 1. Validar JWT
        if (!jwtService.isValid(refreshToken)) {
            throw new RuntimeException("Refresh token inválido");
        }

        // 2. Validar contra BD (AQUÍ VA LO TUYO)
        RefreshToken stored =
                repository.findByToken(refreshToken)
                        .orElseThrow(() ->
                                new RuntimeException("Token no encontrado"));

        if (stored.isRevoked()) {
            throw new RuntimeException("Token revocado");
        }

        if (stored.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado");
        }

        // 3. Extraer usuario
        String username = jwtService.extractUsername(refreshToken);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no existe"));

        // 4. Generar nuevo access token
        String newAccessToken =
                jwtService.generateAccessToken(
                        user.getUsername(),
                        user.getRole().name()
                );

        return new AuthResponse(
                newAccessToken,
                refreshToken
        );
    }
}
