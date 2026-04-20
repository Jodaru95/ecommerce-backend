package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.domain.service.JwtService;
import com.josedavid.ecommerce.app.domain.entity.RefreshToken;
import com.josedavid.ecommerce.app.domain.entity.User;
import com.josedavid.ecommerce.app.application.dto.AuthResponse;
import com.josedavid.ecommerce.app.application.dto.LoginRequest;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.RefreshTokenRepository;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginUseCase {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    public LoginUseCase(UserRepository repository,
                        PasswordEncoder encoder,
                        JwtService jwtService,
                        RefreshTokenRepository refreshTokenRepository) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public AuthResponse execute(LoginRequest request) {

        User user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password incorrecta");
        }

        String accessToken = jwtService.generateAccessToken(
                user.getUsername(),
                user.getRole().name()
        );

        String refreshToken = jwtService.generateRefreshToken(
                user.getUsername()
        );

        RefreshToken entity = new RefreshToken();
        entity.setToken(refreshToken);
        entity.setUser(user);
        entity.setRevoked(false);
        entity.setExpiresAt(LocalDateTime.now().plusDays(7));

        refreshTokenRepository.save(entity);

        return new AuthResponse(accessToken, refreshToken);
    }
}