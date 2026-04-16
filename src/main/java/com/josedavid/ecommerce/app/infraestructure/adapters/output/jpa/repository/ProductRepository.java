package com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository;

import com.josedavid.ecommerce.app.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
