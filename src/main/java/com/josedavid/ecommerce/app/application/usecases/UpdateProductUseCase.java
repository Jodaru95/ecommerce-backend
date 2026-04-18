package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.domain.entity.Product;
import com.josedavid.ecommerce.app.infraestructure.adapters.input.dto.ProductRequest;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.ProductRepository;
import com.josedavid.ecommerce.app.infraestructure.config.ProductNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UpdateProductUseCase {

    private final ProductRepository repository;

    public UpdateProductUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    public Product execute(Long id, ProductRequest request) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        return repository.save(product);
    }
}
