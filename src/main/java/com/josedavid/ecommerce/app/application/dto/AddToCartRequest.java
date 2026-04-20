package com.josedavid.ecommerce.app.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {
    private Long productId;
    private Integer quantity;
}
