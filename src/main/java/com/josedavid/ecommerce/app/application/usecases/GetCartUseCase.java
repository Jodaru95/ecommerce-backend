package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.application.dto.CartItemResponse;
import com.josedavid.ecommerce.app.application.dto.CartResponse;
import com.josedavid.ecommerce.app.domain.entity.Cart;
import com.josedavid.ecommerce.app.domain.entity.CartItem;
import com.josedavid.ecommerce.app.domain.entity.User;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.CartRepository;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    public CartResponse execute(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        List<CartItemResponse> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cart.getItems()) {
            BigDecimal subtotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            CartItemResponse dto = new CartItemResponse();

            dto.setItemId(item.getId());
            dto.setProductId(item.getProduct().getId());
            dto.setName(item.getProduct().getName());
            dto.setPrice(item.getProduct().getPrice());
            dto.setQuantity(item.getQuantity());
            dto.setSubtotal(subtotal);

            items.add(dto);

            total = total.add(subtotal);
        }

        CartResponse response = new CartResponse();
        response.setItems(items);
        response.setTotal(total);

        return response;
    }
}