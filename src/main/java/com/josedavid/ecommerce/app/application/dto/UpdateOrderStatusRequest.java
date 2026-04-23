package com.josedavid.ecommerce.app.application.dto;

import com.josedavid.ecommerce.app.domain.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusRequest {
    private OrderStatus status;
}
