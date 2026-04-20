package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.application.service.JwtService;
import com.josedavid.ecommerce.app.domain.entity.RefreshToken;
import com.josedavid.ecommerce.app.domain.entity.User;
import com.josedavid.ecommerce.app.infraestructure.adapters.input.dto.AuthResponse;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RefreshTokenUseCase {

    private final JwtService jwtService;
    private final RefreshTokenRepository repository;

    public RefreshTokenUseCase(
            JwtService jwtService,
            RefreshTokenRepository repository) {

        this.jwtService = jwtService;
        this.repository = repository;
    }

    public AuthResponse execute(String refreshToken) {

        if (!jwtService.isValid(refreshToken)) {
            throw new RuntimeException("Refresh token inválido");
        }

        RefreshToken tokenEntity = repository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Token no encontrado"));

        if (tokenEntity.isRevoked()) {
            throw new RuntimeException("Token revocado");
        }

        if (tokenEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado");
        }

        User user = tokenEntity.getUser();

        // ROTACIÓN
        repository.delete(tokenEntity);

        String newAccess = jwtService.generateAccessToken(
                user.getUsername(),
                user.getRole().name()
        );

        String newRefresh = jwtService.generateRefreshToken(
                user.getUsername()
        );

        RefreshToken newEntity = new RefreshToken();
        newEntity.setToken(newRefresh);
        newEntity.setUser(user);
        newEntity.setRevoked(false);
        newEntity.setExpiresAt(LocalDateTime.now().plusDays(7));

        repository.save(newEntity);

        return new AuthResponse(newAccess, newRefresh);
    }
}