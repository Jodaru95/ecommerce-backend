package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.domain.entity.Cart;
import com.josedavid.ecommerce.app.domain.entity.User;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.CartRepository;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GetCartUseCase {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public GetCartUseCase(
        CartRepository cartRepository,
        UserRepository userRepository
    ) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public Cart execute(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    }
}
