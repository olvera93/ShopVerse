package com.olvera.shopverseproducto.service;

import com.olvera.shopverseproducto.dto.ProductByCategoryDto;
import com.olvera.shopverseproducto.dto.ProductDetailDto;
import com.olvera.shopverseproducto.dto.ProductRequestDto;
import com.olvera.shopverseproducto.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class AbstractServiceTest {

    protected Product product;

    protected ProductRequestDto productRequestDto;

    protected String productId;

    protected ProductByCategoryDto productByCategoryDto;

    protected String category;

    protected Boolean isActive;

    protected int pageNo;

    protected int pageSize;

    protected ProductDetailDto productDetailDto;

    @BeforeEach
    public void prepare() {

        productId = "UUID1234";

        category = "ELECTRONICS";

        isActive = true;

        pageNo = 5;

        pageSize = 10;

        product = Product.builder()
                .productId(productId)
                .name("iPhone 16")
                .description("it is the best mobile phone in the world")
                .price(BigDecimal.valueOf(1300.00))
                .stock(30)
                .category("ELECTRONIC")
                .isActive(true)
                .imageUrl("http://image.url")
                .createdAt(LocalDateTime.now())
                .build();

        productRequestDto = ProductRequestDto.builder()
                .name("iPhone 16")
                .description("Test Description")
                .price(BigDecimal.valueOf(100.0))
                .stock(10)
                .category("Electronics")
                .imageUrl("http://image.url")
                .isActive(true)
                .build();

        productByCategoryDto = ProductByCategoryDto.builder()
                .productId(productId)
                .name("iPhone 16")
                .description("it is the best mobile phone in the world")
                .price(BigDecimal.valueOf(1300.00))
                .stock(30)
                .category("ELECTRONIC")
                .isActive(true)
                .build();

        productDetailDto = ProductDetailDto.builder()
                .productId(productId)
                .name("iPhone 16")
                .description("it is the best mobile phone in the world")
                .price(BigDecimal.valueOf(1300.00))
                .stock(30)
                .category("ELECTRONIC")
                .imageUrl("http://image.url")
                .isActive(true)
                .build();
    }
}
