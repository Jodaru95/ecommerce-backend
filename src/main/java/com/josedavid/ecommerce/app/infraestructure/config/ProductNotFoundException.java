package com.josedavid.ecommerce.app.infraestructure.config;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Producto con id " + id + " no existe");
    }
}
