package com.josedavid.ecommerce.app.infraestructure.adapters.input.rest;

import com.josedavid.ecommerce.app.application.usecases.PayOrderUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PayOrderUseCase payOrderUseCase;

    public PaymentController(PayOrderUseCase payOrderUseCase) {
        this.payOrderUseCase = payOrderUseCase;
    }

    @PostMapping("/card/{orderId}")
    public ResponseEntity<Void> payCard(
            @PathVariable Long orderId
    ) {
        payOrderUseCase.execute(orderId);
        return ResponseEntity.ok().build();
    }
}
