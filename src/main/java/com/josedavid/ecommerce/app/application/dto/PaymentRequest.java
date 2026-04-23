package com.josedavid.ecommerce.app.application.dto;

import com.josedavid.ecommerce.app.domain.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private PaymentMethod paymentMethod;
}