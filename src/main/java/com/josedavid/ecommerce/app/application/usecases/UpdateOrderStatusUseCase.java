package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.domain.entity.Order;
import com.josedavid.ecommerce.app.domain.enums.OrderStatus;
import com.josedavid.ecommerce.app.domain.service.OrderStatusTransitionService;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderStatusUseCase {

    private final OrderRepository orderRepository;
    private final OrderStatusTransitionService transitionService;

    public UpdateOrderStatusUseCase(
            OrderRepository orderRepository,
            OrderStatusTransitionService transitionService
    ) {
        this.orderRepository = orderRepository;
        this.transitionService = transitionService;
    }

    public void execute(Long orderId, OrderStatus newStatus) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        transitionService.validate(order.getStatus(), newStatus);

        order.setStatus(newStatus);

        orderRepository.save(order);
    }
}
