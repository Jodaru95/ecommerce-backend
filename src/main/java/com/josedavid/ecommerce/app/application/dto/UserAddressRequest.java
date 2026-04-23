package com.josedavid.ecommerce.app.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressRequest {
    private String fullName;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String province;
    private String postalCode;
    private String country;
    private Boolean isDefault;
}
