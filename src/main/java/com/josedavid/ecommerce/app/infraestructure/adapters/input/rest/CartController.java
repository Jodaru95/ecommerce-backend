package com.josedavid.ecommerce.app.infraestructure.adapters.input.rest;

import com.josedavid.ecommerce.app.application.dto.AddToCartRequest;
import com.josedavid.ecommerce.app.application.dto.UpdateCartItemRequest;
import com.josedavid.ecommerce.app.application.usecases.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final AddToCartUseCase addToCartUseCase;
    private final UpdateCartItemUseCase updateCartItemUseCase;
    private final RemoveCartItemUseCase removeCartItemUseCase;
    private final GetCartUseCase getCartUseCase;

    public CartController(
        AddToCartUseCase addToCartUseCase,
        UpdateCartItemUseCase updateCartItemUseCase,
        RemoveCartItemUseCase removeCartItemUseCase,
        GetCartUseCase getCartUseCase
    ) {
        this.addToCartUseCase = addToCartUseCase;
        this.updateCartItemUseCase = updateCartItemUseCase;
        this.removeCartItemUseCase = removeCartItemUseCase;
        this.getCartUseCase = getCartUseCase;
    }

    // 🟢 Añadir producto
    @PostMapping("/items")
    public void addToCart(
        Authentication auth,
        @RequestBody AddToCartRequest request
    ) {
        String username = auth.getName();
        addToCartUseCase.execute(username, request);
    }

    // 🟡 Actualizar cantidad
    @PutMapping("/items/{itemId}")
    public void updateItem(
        @PathVariable Long itemId,
        @RequestBody UpdateCartItemRequest request
    ) {
        updateCartItemUseCase.execute(itemId, request);
    }

    // 🔴 Eliminar item
    @DeleteMapping("/items/{itemId}")
    public void removeItem(@PathVariable Long itemId) {
        removeCartItemUseCase.execute(itemId);
    }

    // 🔵 Obtener carrito
    @GetMapping
    public Object getCart(Authentication auth) {
        String username = auth.getName();
        return getCartUseCase.execute(username);
    }
}
