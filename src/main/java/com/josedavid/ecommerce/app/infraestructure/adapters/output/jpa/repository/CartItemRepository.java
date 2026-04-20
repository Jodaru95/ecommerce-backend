package com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository;

import com.josedavid.ecommerce.app.domain.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
