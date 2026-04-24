package com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository;

import com.josedavid.ecommerce.app.application.dto.MonthlySalesResponse;
import com.josedavid.ecommerce.app.domain.entity.Order;
import com.josedavid.ecommerce.app.domain.entity.User;
import com.josedavid.ecommerce.app.domain.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("""
        SELECT new com.josedavid.ecommerce.app.application.dto.MonthlySalesResponse(
            EXTRACT(YEAR FROM o.paidAt),
            EXTRACT(MONTH FROM o.paidAt),
            SUM(o.totalPrice)
        )
        FROM Order o
        WHERE o.status = com.josedavid.ecommerce.app.domain.enums.OrderStatus.PAID
        GROUP BY EXTRACT(YEAR FROM o.paidAt), EXTRACT(MONTH FROM o.paidAt)
        ORDER BY EXTRACT(YEAR FROM o.paidAt), EXTRACT(MONTH FROM o.paidAt)
    """)
    List<MonthlySalesResponse> getMonthlySales();

    Page<Order> findAll(Pageable pageable);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    Page<Order> findByStatusAndUserUsernameContainingIgnoreCase(
            OrderStatus status,
            String username,
            Pageable pageable
    );

    Page<Order> findByUserUsernameContainingIgnoreCase(
            String username,
            Pageable pageable
    );
}
