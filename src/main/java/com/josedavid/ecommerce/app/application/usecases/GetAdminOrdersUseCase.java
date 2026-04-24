package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.application.dto.AdminOrderResponse;
import com.josedavid.ecommerce.app.domain.entity.Order;
import com.josedavid.ecommerce.app.domain.enums.OrderStatus;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.OrderRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class GetAdminOrdersUseCase {

    private final OrderRepository repository;

    public GetAdminOrdersUseCase(OrderRepository repository) {
        this.repository = repository;
    }

    public Page<AdminOrderResponse> execute(
            int page,
            int size,
            String status,
            String username
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        Page<Order> orders;

        boolean hasStatus = status != null && !status.isBlank();
        boolean hasUsername = username != null && !username.isBlank();

        if (hasStatus && hasUsername) {

            orders = repository.findByStatusAndUserUsernameContainingIgnoreCase(
                    OrderStatus.valueOf(status),
                    username,
                    pageable
            );

        } else if (hasStatus) {

            orders = repository.findByStatus(
                    OrderStatus.valueOf(status),
                    pageable
            );

        } else if (hasUsername) {

            orders = repository.findByUserUsernameContainingIgnoreCase(
                    username,
                    pageable
            );

        } else {

            orders = repository.findAll(pageable);
        }

        return orders.map(order -> new AdminOrderResponse(
                order.getId(),
                order.getUser().getUsername(),
                order.getStatus().name(),
                order.getTotalPrice(),
                order.getCreatedAt()
        ));
    }
}