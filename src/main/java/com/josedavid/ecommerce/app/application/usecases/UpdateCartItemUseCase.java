package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.application.dto.UpdateCartItemRequest;
import com.josedavid.ecommerce.app.domain.entity.CartItem;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.CartItemRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateCartItemUseCase {
    private final CartItemRepository cartItemRepository;

    public UpdateCartItemUseCase(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public void execute(Long itemId, UpdateCartItemRequest request) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        if (request.getQuantity() <= 0) {
            cartItemRepository.delete(item);
            return;
        }

        item.setQuantity(request.getQuantity());
        cartItemRepository.save(item);
    }
}
