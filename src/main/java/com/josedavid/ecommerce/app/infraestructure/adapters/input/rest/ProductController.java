package com.josedavid.ecommerce.app.infraestructure.adapters.input.rest;

import com.josedavid.ecommerce.app.application.usecases.GetProductsUseCase;
import com.josedavid.ecommerce.app.domain.entity.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final GetProductsUseCase getProductsUseCase;

    public ProductController(GetProductsUseCase getProductsUseCase) {
        this.getProductsUseCase = getProductsUseCase;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return getProductsUseCase.execute();
    }
}
