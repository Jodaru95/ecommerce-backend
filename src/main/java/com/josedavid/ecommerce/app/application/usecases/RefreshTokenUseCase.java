package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.application.service.JwtService;
import com.josedavid.ecommerce.app.domain.entity.User;
import com.josedavid.ecommerce.app.infraestructure.adapters.input.dto.AuthResponse;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenUseCase {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public RefreshTokenUseCase(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public AuthResponse execute(String refreshToken) {
        if (!jwtService.isValid(refreshToken)) {
            throw new RuntimeException("Refresh token inválido");
        }
        String username = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no existe"));
        String newAccessToken =
            jwtService.generateToken(
                user.getUsername(),
                user.getRole().name()
            );
        return new AuthResponse(
            newAccessToken,
            refreshToken
        );
    }
}
