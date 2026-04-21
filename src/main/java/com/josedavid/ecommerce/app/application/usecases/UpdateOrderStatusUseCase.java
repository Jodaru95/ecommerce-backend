package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.domain.entity.Order;
import com.josedavid.ecommerce.app.domain.enums.OrderStatus;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderStatusUseCase {
    private final OrderRepository repository;

    public UpdateOrderStatusUseCase(OrderRepository repository) {
        this.repository = repository;
    }

    public void execute(Long id, String status) {

        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));

        repository.save(order);
    }
}
