package com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository;

import com.josedavid.ecommerce.app.domain.entity.RefreshToken;
import com.josedavid.ecommerce.app.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}
