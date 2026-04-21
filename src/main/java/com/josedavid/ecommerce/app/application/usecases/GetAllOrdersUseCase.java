package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.domain.entity.Order;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllOrdersUseCase {
    private final OrderRepository repository;

    public GetAllOrdersUseCase(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> execute() {
        return repository.findAll();
    }
}
