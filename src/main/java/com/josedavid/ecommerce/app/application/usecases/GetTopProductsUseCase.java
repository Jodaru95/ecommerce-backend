package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.application.dto.TopProductResponse;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetTopProductsUseCase {

    private final OrderItemRepository repository;

    public GetTopProductsUseCase(OrderItemRepository repository) {
        this.repository = repository;
    }

    public List<TopProductResponse> execute() {
        return repository.findTopProducts();
    }
}