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
    private String addressLine1;

    @NotBlank
    private String city;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String country;
}
