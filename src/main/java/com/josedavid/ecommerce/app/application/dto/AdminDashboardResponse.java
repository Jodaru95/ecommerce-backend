package com.josedavid.ecommerce.app.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class AdminDashboardResponse {

    private long totalOrders;
    private long pendingOrders;
    private long paidOrders;
    private long cancelledOrders;

    private BigDecimal totalRevenue;

    private long totalUsers;

    private long productsOutOfStock;
    private long productsLowStock;
}