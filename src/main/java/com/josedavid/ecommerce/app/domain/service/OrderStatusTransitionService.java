package com.josedavid.ecommerce.app.domain.service;

import com.josedavid.ecommerce.app.domain.enums.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class OrderStatusTransitionService {

    private static final Map<OrderStatus, Set<OrderStatus>> transitions = Map.of(

            OrderStatus.PENDING_PAYMENT,
            Set.of(OrderStatus.PAID, OrderStatus.CANCELLED),

            OrderStatus.PAID,
            Set.of(OrderStatus.PROCESSING, OrderStatus.REFUNDED),

            OrderStatus.PROCESSING,
            Set.of(OrderStatus.SHIPPED, OrderStatus.CANCELLED),

            OrderStatus.SHIPPED,
            Set.of(OrderStatus.DELIVERED),

            OrderStatus.DELIVERED,
            Set.of(OrderStatus.REFUNDED),

            OrderStatus.CANCELLED,
            Set.of(),

            OrderStatus.REFUNDED,
            Set.of()
    );

    public void validate(OrderStatus current, OrderStatus target) {

        if (!transitions.getOrDefault(current, Set.of()).contains(target)) {
            throw new RuntimeException(
                    "Cambio de estado inválido: " +
                            current + " -> " + target
            );
        }
    }
}