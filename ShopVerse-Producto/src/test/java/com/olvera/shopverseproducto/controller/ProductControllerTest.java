package com.olvera.shopverseproducto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olvera.shopverseproducto.dto.PageResponse;
import com.olvera.shopverseproducto.dto.ProductByCategoryDto;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    void shouldReturnRetrieved_WhenCategoryExists() throws Exception {

        String category = "ELECTRONICS";
        int pageNo = 0;
        int pageSize = 1;

        ProductByCategoryDto productByCategoryDto = ProductByCategoryDto.builder()
                .productId("12321312k")
                .name("iPhone 16")
                .description("it is the best mobile phone in the world")
                .price(BigDecimal.valueOf(1300.00))
                .stock(30)
                .category("ELECTRONICS")
                .build();

        List<ProductByCategoryDto> dtoList = List.of(productByCategoryDto);

        PageResponse pageResponse = new PageResponse(
                dtoList,
                pageNo,
                pageSize,
                1L,
                1,
                true
        );
        when(productService.getProductsByCategory(category, pageNo, pageSize)).thenReturn(pageResponse);

        mockMvc.perform(get("/api/v1/products")
                .param("category", category)
                .param("pageNo", String.valueOf(pageNo))
                .param("pageSize", String.valueOf(pageSize))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("iPhone 16"))
                .andExpect(jsonPath("$.pageNo").value(0))
                .andExpect(jsonPath("$.pageSize").value(1))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.last").value(true));
    }


    @TestConfiguration
    static class TestConfig {

        @Bean
        public IProductService productService() {
            return Mockito.mock(IProductService.class);
        }
    }
}
