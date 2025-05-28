package com.olvera.shopverseproducto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olvera.shopverseproducto.dto.ProductRequestDto;
import com.olvera.shopverseproducto.dto.ProductResponseDto;
import com.olvera.shopverseproducto.service.IProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Import(ProductControllerTest.TestConfig.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IProductService productService;

    @Test
    void shouldReturnCreated_WhenProductIsValid() throws Exception {

        ProductRequestDto requestDto = ProductRequestDto.builder()
                .name("Test Product")
                .description("This is a test product")
                .price(BigDecimal.valueOf(99.99))
                .stock(100)
                .category("Electronics")
                .imageUrl("http://example.com/image.jpg")
                .isActive(true)
                .build();

        ProductResponseDto responseDto = ProductResponseDto.builder()
                .statusMsg("Product was creating successfully!!")
                .build();

        when(productService.createProduct(any())).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/createProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusMsg").value("Product was creating successfully!!"));
    }


    @TestConfiguration
    static class TestConfig {

        @Bean
        public IProductService productService() {
            return Mockito.mock(IProductService.class);
        }
    }
}
