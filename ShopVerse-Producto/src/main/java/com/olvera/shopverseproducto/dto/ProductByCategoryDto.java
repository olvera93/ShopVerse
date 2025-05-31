package com.olvera.shopverseproducto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(
        name = "Products",
        description = "Schema to hold Product information"
)
public class ProductByCategoryDto {

    private String productId;

    @Schema(description = "Name of product", example = "LG 34'")
    @NotNull(message = "Name cannot be null or empty")
    @Size(min = 3, max = 30)
    private String name;

    @Schema(description = "Description of product", example = "It is a great tv with the best screen")
    @NotNull(message = "Description cannot be null or empty")
    @Size(min = 10, max = 60)
    private String description;

    @Schema(description = "Price of product", example = "59.99")
    @NotNull(message = "Price cannot be null or empty")
    @DecimalMin("0.01")
    private BigDecimal price;

    @Schema(description = "Stock of product", example = "5")
    @NotNull(message = "Stock cannot be null or empty")
    @Min(value = 0, message = "Stock cannot have less to 0")
    private Integer stock;

    @Schema(description = "Image of product", example = "https://LG_34.com")
    @NotNull(message = "Image cannot be null or empty")
    private String imageUrl;

    private String category;

    private Boolean isActive;
}
