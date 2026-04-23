package com.josedavid.ecommerce.app.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveAddressRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    private String phone;

    @NotBlank
    private String addressLine1;

    private String addressLine2;

    @NotBlank
    private String city;

    private String province;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String country;

    private Boolean isDefault;
}