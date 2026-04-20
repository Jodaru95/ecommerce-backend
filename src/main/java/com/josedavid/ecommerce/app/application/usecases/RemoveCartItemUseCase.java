package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.domain.entity.CartItem;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.CartItemRepository;
import org.springframework.stereotype.Service;

@Service
public class RemoveCartItemUseCase {
    private final CartItemRepository cartItemRepository;

    public RemoveCartItemUseCase(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public void execute(Long itemId) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        cartItemRepository.delete(item);
    }
}
