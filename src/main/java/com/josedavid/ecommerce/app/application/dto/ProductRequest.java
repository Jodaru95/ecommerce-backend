package com.josedavid.ecommerce.app.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {

    private String description;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", message = "El precio debe ser mayor o igual a 0")
    private BigDecimal price;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

}
