package com.josedavid.ecommerce.app.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderDetailResponse {
    private Long id;
    private String status;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;

    private String shippingFullName;
    private String shippingAddressLine1;
    private String shippingCity;
    private String shippingPostalCode;
    private String shippingCountry;

    private List<OrderItemResponse> items;
}
