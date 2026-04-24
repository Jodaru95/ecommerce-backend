package com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository;

import com.josedavid.ecommerce.app.domain.entity.Order;
import com.josedavid.ecommerce.app.domain.entity.User;
import com.josedavid.ecommerce.app.domain.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserUsernameOrderByCreatedAtDesc(String username);
    List<Order> findByUserOrderByCreatedAtDesc(User user);

    long countByStatus(OrderStatus status);

    @Query("""
        SELECT COALESCE(SUM(o.totalPrice), 0)
        FROM Order o
        WHERE o.status = 'PAID'
    """)
    BigDecimal getTotalRevenue();
}
