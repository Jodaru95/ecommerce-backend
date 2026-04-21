package com.josedavid.ecommerce.app.infraestructure.adapters.input.rest;

import com.josedavid.ecommerce.app.application.dto.CheckoutRequest;
import com.josedavid.ecommerce.app.application.usecases.CheckoutUseCase;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CheckoutUseCase checkoutUseCase;

    public OrderController(CheckoutUseCase checkoutUseCase) {
        this.checkoutUseCase = checkoutUseCase;
    }

    @PostMapping("/checkout")
    public Long checkout(
            Authentication auth,
            @RequestBody CheckoutRequest request
    ) {
        return checkoutUseCase.execute(
                auth.getName(),
                request.getShippingAddress()
        );
    }
}
