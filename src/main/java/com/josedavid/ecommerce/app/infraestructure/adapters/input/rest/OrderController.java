package com.josedavid.ecommerce.app.infraestructure.adapters.input.rest;

import com.josedavid.ecommerce.app.application.dto.CheckoutRequest;
import com.josedavid.ecommerce.app.application.dto.PaymentRequest;
import com.josedavid.ecommerce.app.application.dto.UpdateOrderStatusRequest;
import com.josedavid.ecommerce.app.application.usecases.*;
import com.josedavid.ecommerce.app.domain.entity.Order;
import com.josedavid.ecommerce.app.domain.enums.OrderStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CheckoutUseCase checkoutUseCase;
    private final GetMyOrdersUseCase getMyOrdersUseCase;
    private final GetAllOrdersUseCase getAllOrdersUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final PayOrderUseCase payOrderUseCase;

    public OrderController(CheckoutUseCase checkoutUseCase, GetMyOrdersUseCase getMyOrdersUseCase, GetAllOrdersUseCase getAllOrdersUseCase, UpdateOrderStatusUseCase updateOrderStatusUseCase, PayOrderUseCase payOrderUseCase) {
        this.checkoutUseCase = checkoutUseCase;
        this.getMyOrdersUseCase = getMyOrdersUseCase;
        this.getAllOrdersUseCase = getAllOrdersUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
        this.payOrderUseCase = payOrderUseCase;
    }

    @PostMapping("/checkout")
    public Long checkout(
            Authentication auth,
            @RequestBody CheckoutRequest request
    ) {
        return checkoutUseCase.execute(auth.getName(), request);
    }

    @GetMapping("/my-orders")
    public List<Order> myOrders(Authentication auth) {
        return getMyOrdersUseCase.execute(auth.getName());
    }

    @GetMapping
    public List<Order> allOrders() {
        return getAllOrdersUseCase.execute();
    }

    @PutMapping("/{id}/status")
    public void updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequest request
    ) {
        updateOrderStatusUseCase.execute(id, request.getStatus());
    }

}
