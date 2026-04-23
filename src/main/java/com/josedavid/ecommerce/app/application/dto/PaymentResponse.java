package com.josedavid.ecommerce.app.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResponse {
    private Long orderId;
    private String status;
    private String transactionId;
}