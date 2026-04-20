package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.domain.entity.RefreshToken;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class LogoutUseCase {

    private final RefreshTokenRepository repository;

    public LogoutUseCase(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    public void execute(String token) {

        RefreshToken entity =
                repository.findByToken(token)
                        .orElseThrow(() ->
                                new RuntimeException("Token no encontrado"));

        entity.setRevoked(true);

        repository.save(entity);
    }
}
