package com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository;

import com.josedavid.ecommerce.app.application.dto.TopProductResponse;
import com.josedavid.ecommerce.app.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("""
        SELECT new com.josedavid.ecommerce.app.application.dto.TopProductResponse(
            oi.product.id,
            oi.product.name,
            SUM(oi.quantity)
        )
        FROM OrderItem oi
        WHERE oi.order.status = 'PAID'
        GROUP BY oi.product.id, oi.product.name
        ORDER BY SUM(oi.quantity) DESC
    """)
    List<TopProductResponse> findTopProducts();
}