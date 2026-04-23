package com.josedavid.ecommerce.app.application.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
    private Long id;
    private String fullName;
    private String addressLine1;
    private String city;
    private String postalCode;
    private String country;
    private Boolean isDefault;
}
