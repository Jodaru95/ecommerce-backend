package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.application.dto.OrderSummaryResponse;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetMyOrdersUseCase {

    private final OrderRepository repository;

    public GetMyOrdersUseCase(OrderRepository repository) {
        this.repository = repository;
    }

    public List<OrderSummaryResponse> execute(String username) {

        return repository.findByUserUsernameOrderByCreatedAtDesc(username)
                .stream()
                .map(order -> new OrderSummaryResponse(
                        order.getId(),
                        order.getStatus().name(),
                        order.getTotalPrice(),
                        order.getCreatedAt()
                ))
                .toList();
    }
}