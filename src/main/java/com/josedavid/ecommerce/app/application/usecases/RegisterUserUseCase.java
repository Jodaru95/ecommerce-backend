package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.application.service.JwtService;
import com.josedavid.ecommerce.app.domain.entity.Role;
import com.josedavid.ecommerce.app.domain.entity.User;
import com.josedavid.ecommerce.app.infraestructure.adapters.input.dto.AuthResponse;
import com.josedavid.ecommerce.app.infraestructure.adapters.input.dto.RegisterRequest;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public RegisterUserUseCase(
            UserRepository repository,
            PasswordEncoder encoder,
            JwtService jwtService
    ) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public AuthResponse execute(RegisterRequest request) {

        if (repository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username ya existe");
        }

        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email ya existe");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        repository.save(user);

        String accessToken = jwtService.generateAccessToken(
                user.getUsername(),
                user.getRole().name()
        );

        String refreshToken = jwtService.generateRefreshToken(
                user.getUsername()
        );

        return new AuthResponse(accessToken, refreshToken);
    }
}
