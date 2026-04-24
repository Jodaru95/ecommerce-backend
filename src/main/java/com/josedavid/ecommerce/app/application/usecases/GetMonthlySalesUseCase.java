package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.application.dto.MonthlySalesResponse;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetMonthlySalesUseCase {

    private final OrderRepository repository;

    public GetMonthlySalesUseCase(OrderRepository repository) {
        this.repository = repository;
    }

    public List<MonthlySalesResponse> execute() {
        return repository.getMonthlySales();
    }
}