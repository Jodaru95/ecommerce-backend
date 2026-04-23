package com.josedavid.ecommerce.app.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutRequest {
    private String shippingAddress;
    private Long addressId;
}
