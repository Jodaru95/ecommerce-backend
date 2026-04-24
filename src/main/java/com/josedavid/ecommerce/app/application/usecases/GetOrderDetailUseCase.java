package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.application.dto.OrderDetailResponse;
import com.josedavid.ecommerce.app.application.dto.OrderItemResponse;
import com.josedavid.ecommerce.app.domain.entity.Order;
import com.josedavid.ecommerce.app.domain.entity.OrderItem;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.OrderRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetOrderDetailUseCase {

    private final OrderRepository repository;

    public GetOrderDetailUseCase(OrderRepository repository) {
        this.repository = repository;
    }

    public OrderDetailResponse execute(Long orderId, String username, boolean isAdmin) {

        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (!isAdmin &&
                !order.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("Sin permisos");
        }

        List<OrderItemResponse> items = order.getItems()
                .stream()
                .map(this::mapItem)
                .toList();

        return new OrderDetailResponse(
                order.getId(),
                order.getStatus().name(),
                order.getTotalPrice(),
                order.getCreatedAt(),
                order.getShippingFullName(),
                order.getShippingAddressLine1(),
                order.getShippingCity(),
                order.getShippingPostalCode(),
                order.getShippingCountry(),
                items
        );
    }

    private OrderItemResponse mapItem(OrderItem item) {
        return new OrderItemResponse(
                item.getProduct().getName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getSubtotal()
        );
    }
}