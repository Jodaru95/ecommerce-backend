package com.josedavid.ecommerce.app.infraestructure.adapters.input.rest;

import com.josedavid.ecommerce.app.application.dto.AddToCartRequest;
import com.josedavid.ecommerce.app.application.dto.UpdateCartItemRequest;
import com.josedavid.ecommerce.app.application.dto.CartResponse;
import com.josedavid.ecommerce.app.application.usecases.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cart", description = "Carrito de compra")
@RestController
@RequestMapping("/cart")
@PreAuthorize("hasRole('USER')")
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

    @Operation(summary = "Añadir producto al carrito")
    @PostMapping("/items")
    public ResponseEntity<Void> addToCart(
        Authentication auth,
        @RequestBody AddToCartRequest request
    ) {
        String username = auth.getName();
        addToCartUseCase.execute(username, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Actualizar cantidad de un producto del carrito")
    @PutMapping("/items/{itemId}")
    public ResponseEntity<Void> updateItem(
        @PathVariable Long itemId,
        @RequestBody UpdateCartItemRequest request
    ) {
        updateCartItemUseCase.execute(itemId, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar producto del carrito")
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long itemId) {
        removeCartItemUseCase.execute(itemId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Obtener carrito actual del usuario")
    @GetMapping
    public ResponseEntity<CartResponse> getCart(Authentication auth) {
        String username = auth.getName();
        return ResponseEntity.ok(getCartUseCase.execute(auth.getName()));
    }
}
