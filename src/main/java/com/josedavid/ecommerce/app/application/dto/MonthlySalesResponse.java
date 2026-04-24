package com.josedavid.ecommerce.app.application.dto;

import java.math.BigDecimal;

public record MonthlySalesResponse(
        Integer year,
        Integer month,
        BigDecimal revenue
) {}