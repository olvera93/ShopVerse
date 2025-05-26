package com.olvera.shopverseproducto.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    @Id
    private String productId;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;

    private String category;

    private String imageUrl;

    private Boolean isActive;
}
