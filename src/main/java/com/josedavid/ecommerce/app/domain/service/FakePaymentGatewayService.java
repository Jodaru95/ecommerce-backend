package com.josedavid.ecommerce.app.domain.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FakePaymentGatewayService {

    public String chargeCard(Long orderId) {
        return "TXN-" + orderId + "-" + UUID.randomUUID()
                .toString()
                .substring(0,8)
                .toUpperCase();
    }
}
