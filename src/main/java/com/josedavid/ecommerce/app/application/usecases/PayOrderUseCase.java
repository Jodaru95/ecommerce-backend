package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.application.dto.PaymentRequest;
import com.josedavid.ecommerce.app.domain.entity.Order;
import com.josedavid.ecommerce.app.domain.enums.OrderStatus;
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
    public void execute(Long orderId, PaymentRequest request) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        transitionService.validate(
                order.getStatus(),
                OrderStatus.PAID
        );

        order.setStatus(OrderStatus.PAID);
        order.setPaymentMethod(request.getPaymentMethod());
        order.setPaymentTransactionId(UUID.randomUUID().toString());
        order.setPaidAt(LocalDateTime.now());

        orderRepository.save(order);
    }
}