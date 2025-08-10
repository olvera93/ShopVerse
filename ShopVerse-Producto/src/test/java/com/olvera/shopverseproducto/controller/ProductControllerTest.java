package com.olvera.shopverseproducto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olvera.shopverseproducto.dto.*;
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

    @Test
    void shouldUpdateProduct_WhenProductExists() throws Exception {

        String productId = "12345";
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .name("Updated Product")
                .description("This is an updated product")
                .price(BigDecimal.valueOf(89.99))
                .stock(50)
                .category("Electronics")
                .imageUrl("http://example.com/updated-image.jpg")
                .isActive(true)
                .build();

        ProductResponseDto responseDto = ProductResponseDto.builder()
                .statusMsg("Product was updated successfully!!")
                .build();

        when(productService.updateProduct(productId, requestDto)).thenReturn(responseDto);

        mockMvc.perform(put("/api/v1/updateProduct/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusMsg").value("Product was updated successfully!!"));
    }

    @Test
    void shouldDeactivateProduct_WhenProductExists() throws Exception {
        String productId = "12345";

        ProductResponseDto responseDto = ProductResponseDto.builder()
                .statusMsg("Product was deactivated successfully!!")
                .statusCode("200")
                .build();

        when(productService.deactivateProduct(productId)).thenReturn(responseDto);

        mockMvc.perform(patch("/api/v1/deactivateProduct/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusMsg").value("Product was deactivated successfully!!"))
                .andExpect(jsonPath("$.statusCode").value("200"));
    }

    @Test
    void shouldReturnProductDetail_WhenProductExists() throws Exception {
        String productId = "12345";

        ProductDetailDto responseDto = ProductDetailDto.builder()
                .productId(productId)
                .name("Test Product")
                .description("This is a test product")
                .price(BigDecimal.valueOf(99.99))
                .stock(100)
                .category("Electronics")
                .imageUrl("http://example.com/image.jpg")
                .isActive(true)
                .build();

        when(productService.getProductDetail(productId)).thenReturn(responseDto);

        mockMvc.perform(get("/api/v1/productDetail/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.description").value("This is a test product"))
                .andExpect(jsonPath("$.price").value(99.99))
                .andExpect(jsonPath("$.stock").value(100))
                .andExpect(jsonPath("$.category").value("Electronics"))
                .andExpect(jsonPath("$.imageUrl").value("http://example.com/image.jpg"))
                .andExpect(jsonPath("$.isActive").value(true));

    }



    @TestConfiguration
    static class TestConfig {

        @Bean
        public IProductService productService() {
            return Mockito.mock(IProductService.class);
        }
    }
}
