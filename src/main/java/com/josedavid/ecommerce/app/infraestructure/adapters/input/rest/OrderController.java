package com.josedavid.ecommerce.app.infraestructure.adapters.input.rest;

import com.josedavid.ecommerce.app.application.dto.CheckoutRequest;
import com.josedavid.ecommerce.app.application.dto.OrderDetailResponse;
import com.josedavid.ecommerce.app.application.dto.OrderSummaryResponse;
import com.josedavid.ecommerce.app.application.dto.UpdateOrderStatusRequest;
import com.josedavid.ecommerce.app.application.usecases.*;
import com.josedavid.ecommerce.app.domain.entity.Order;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final GetOrderDetailUseCase getOrderDetailUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;

    public OrderController(CheckoutUseCase checkoutUseCase, GetMyOrdersUseCase getMyOrdersUseCase, GetAllOrdersUseCase getAllOrdersUseCase, UpdateOrderStatusUseCase updateOrderStatusUseCase, PayOrderUseCase payOrderUseCase, GetOrderDetailUseCase getOrderDetailUseCase, CancelOrderUseCase cancelOrderUseCase) {
        this.checkoutUseCase = checkoutUseCase;
        this.getMyOrdersUseCase = getMyOrdersUseCase;
        this.getAllOrdersUseCase = getAllOrdersUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
        this.payOrderUseCase = payOrderUseCase;
        this.getOrderDetailUseCase = getOrderDetailUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
    }

    @PostMapping("/checkout")
    @PreAuthorize("hasRole('USER')")
    public Long checkout(
            Authentication auth,
            @RequestBody CheckoutRequest request
    ) {
        return checkoutUseCase.execute(auth.getName(), request);
    }

    @GetMapping("/my-orders")
    @PreAuthorize("hasRole('USER')")
    public List<OrderSummaryResponse> myOrders(Authentication auth) {
        return getMyOrdersUseCase.execute(auth.getName());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Order> allOrders() {
        return getAllOrdersUseCase.execute();
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequest request
    ) {
        updateOrderStatusUseCase.execute(id, request.getStatus());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public OrderDetailResponse detail(
        @PathVariable Long id,
        Authentication auth
    ) {
        boolean isAdmin = auth.getAuthorities()
            .stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        return getOrderDetailUseCase.execute(id, auth.getName(), isAdmin);
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasRole('USER')")
    public void cancel(
            @PathVariable Long id,
            Authentication auth
    ) {
        cancelOrderUseCase.execute(id, auth.getName());
    }

}
