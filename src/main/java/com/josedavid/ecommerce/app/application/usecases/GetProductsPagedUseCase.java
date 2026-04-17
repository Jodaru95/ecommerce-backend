package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.domain.entity.Product;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetProductsPagedUseCase {

    private final ProductRepository repository;

    public GetProductsPagedUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    public Page<Product> execute(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
