package com.josedavid.ecommerce.app.application.usecases;


import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.ProductRepository;
import com.josedavid.ecommerce.app.infraestructure.config.ProductNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteProductUseCase {

    private final ProductRepository repository;

    public DeleteProductUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    public void execute(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
