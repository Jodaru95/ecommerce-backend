package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.domain.entity.Order;
import com.josedavid.ecommerce.app.domain.entity.OrderItem;
import com.josedavid.ecommerce.app.domain.entity.Product;
import com.josedavid.ecommerce.app.domain.enums.OrderStatus;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CancelOrderUseCase {

    private final OrderRepository repository;

    public CancelOrderUseCase(OrderRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void execute(Long orderId, String username) {

        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (!order.getUser().getUsername().equals(username)) {
            throw new RuntimeException("No puedes cancelar este pedido");
        }

        if (order.getStatus() != OrderStatus.PENDING_PAYMENT &&
                order.getStatus() != OrderStatus.PAID) {

            throw new RuntimeException("Pedido no cancelable");
        }

        // RESTOCK AUTOMÁTICO
        for (OrderItem item : order.getItems()) {

            Product product = item.getProduct();

            product.setStock(
                    product.getStock() + item.getQuantity()
            );
        }

        order.setStatus(OrderStatus.CANCELLED);

        repository.save(order);
    }
}