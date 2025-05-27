package com.olvera.shopverseproducto.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductRequestDto {

    @NotNull(message = "Name cannot be null or empty")
    @Size(min = 3, max = 30)
    private String name;

    @NotNull(message = "Description cannot be null or empty")
    @Size(min = 10, max = 60)
    private String description;

    @NotNull(message = "Price cannot be null or empty")
    @DecimalMin("0.01")
    private BigDecimal price;

    @NotNull(message = "Stock cannot be null or empty")
    @Min(value = 0, message = "Stock cannot have less to 0")
    private Integer stock;

    @NotNull(message = "Category cannot be null or empty")
    private String category;

    @NotNull(message = "Image cannot be null or empty")
    private String imageUrl;

    private Boolean isActive;

}
