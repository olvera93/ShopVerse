package com.olvera.shopverseproducto.service;

import com.olvera.shopverseproducto.dto.ProductResponseDto;
import com.olvera.shopverseproducto.exception.ResourceAlreadyExist;
import com.olvera.shopverseproducto.model.Product;
import com.olvera.shopverseproducto.repository.ProductRepository;
import com.olvera.shopverseproducto.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest extends AbstractServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void createProduct_ShouldCreateSuccessfully_WhenProductDoesNotExist() {

        when(productRepository.findByName(productRequestDto.getName())).thenReturn(Optional.empty());

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponseDto responseDto = productService.createProduct(productRequestDto);

        assertEquals("Product was creating successfully!!", responseDto.getStatusMsg());
    }

    @Test
    void createProduct_ShouldThrowException_WhenProductAlreadyExists() {

        when(productRepository.findByName(productRequestDto.getName())).thenReturn(Optional.of(product));

        ResourceAlreadyExist exception = assertThrows(ResourceAlreadyExist.class, () ->
                productService.createProduct(productRequestDto));

        assertEquals("Product already exists the given input data name: '"+ productRequestDto.getName()+ "'", exception.getMessage());

        verify(productRepository).findByName(productRequestDto.getName());
        verify(productRepository, never()).save(any());
    }

}
