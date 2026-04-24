package com.josedavid.ecommerce.app.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AdminOrderResponse(
        Long id,
        String username,
        String status,
        BigDecimal totalPrice,
        LocalDateTime createdAt
) {
}