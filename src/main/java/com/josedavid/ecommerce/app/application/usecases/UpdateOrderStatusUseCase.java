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

    private void validateTransition(OrderStatus current, OrderStatus next) {

        if (current == OrderStatus.DELIVERED ||
                current == OrderStatus.CANCELLED ||
                current == OrderStatus.REFUNDED) {

            throw new RuntimeException("Pedido cerrado, no modificable");
        }

        if (current == OrderStatus.PENDING_PAYMENT &&
                next != OrderStatus.PAID &&
                next != OrderStatus.CANCELLED) {
            throw new RuntimeException("Transición inválida");
        }

        if (current == OrderStatus.PAID &&
                next != OrderStatus.PROCESSING &&
                next != OrderStatus.CANCELLED &&
                next != OrderStatus.REFUNDED) {
            throw new RuntimeException("Transición inválida");
        }

        if (current == OrderStatus.PROCESSING &&
                next != OrderStatus.SHIPPED &&
                next != OrderStatus.CANCELLED) {
            throw new RuntimeException("Transición inválida");
        }

        if (current == OrderStatus.SHIPPED &&
                next != OrderStatus.DELIVERED) {
            throw new RuntimeException("Transición inválida");
        }
    }
}
