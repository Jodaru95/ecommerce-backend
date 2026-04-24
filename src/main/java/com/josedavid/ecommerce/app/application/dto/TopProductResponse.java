package com.josedavid.ecommerce.app.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TopProductResponse {
    private Long productId;
    private String productName;
    private Long unitsSold;
}