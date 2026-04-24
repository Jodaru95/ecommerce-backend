package com.josedavid.ecommerce.app.infraestructure.adapters.input.rest;

import com.josedavid.ecommerce.app.application.usecases.PayOrderUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payments", description = "Gestión de pagos")
@RestController
@RequestMapping("/payments")
@PreAuthorize("hasRole('USER')")
public class PaymentController {

    private final PayOrderUseCase payOrderUseCase;

    public PaymentController(PayOrderUseCase payOrderUseCase) {
        this.payOrderUseCase = payOrderUseCase;
    }

    @Operation(
        summary = "Pagar pedido con tarjeta",
        description = "Simula el pago y marca el pedido como pagado"
    )
    @PostMapping("/card/{orderId}")
    public ResponseEntity<Void> payCard(
            @PathVariable Long orderId
    ) {
        payOrderUseCase.execute(orderId);
        return ResponseEntity.ok().build();
    }
}
