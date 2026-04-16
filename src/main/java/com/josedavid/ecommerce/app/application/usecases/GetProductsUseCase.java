package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.domain.entity.Product;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetProductsUseCase {

    private final ProductRepository productRepository;

    public GetProductsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> execute() {
        return productRepository.findAll();
    }
}
