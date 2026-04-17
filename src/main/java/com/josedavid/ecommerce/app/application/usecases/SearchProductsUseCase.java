package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.domain.entity.Product;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.ProductRepository;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.specification.ProductSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SearchProductsUseCase {

    private final ProductRepository repository;

    public SearchProductsUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> execute(
            String name,
            BigDecimal minPrice,
            BigDecimal maxPrice) {

        Specification<Product> spec =
                Specification.where(ProductSpecification.hasName(name))
                        .and(ProductSpecification.minPrice(minPrice))
                        .and(ProductSpecification.maxPrice(maxPrice));

        return repository.findAll(spec);
    }
}
