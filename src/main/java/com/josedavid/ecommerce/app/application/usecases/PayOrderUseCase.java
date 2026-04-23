package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.domain.entity.Order;
import com.josedavid.ecommerce.app.domain.enums.OrderStatus;
import com.josedavid.ecommerce.app.domain.enums.PaymentMethod;
import com.josedavid.ecommerce.app.domain.service.OrderStatusTransitionService;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PayOrderUseCase {

    private final OrderRepository orderRepository;
    private final OrderStatusTransitionService transitionService;

    public PayOrderUseCase(
            OrderRepository orderRepository,
            OrderStatusTransitionService transitionService
    ) {
        this.orderRepository = orderRepository;
        this.transitionService = transitionService;
    }

    @Transactional
    public void execute(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        transitionService.validate(
                order.getStatus(),
                OrderStatus.PAID
        );

        if (order.getPaymentMethod() != PaymentMethod.CARD) {
            throw new RuntimeException("Pedido no configurado para tarjeta");
        }

        order.setStatus(OrderStatus.PAID);
        order.setPaymentTransactionId(UUID.randomUUID().toString());
        order.setPaidAt(LocalDateTime.now());

        orderRepository.save(order);
    }
}