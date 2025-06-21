package com.olvera.shopverseproducto.service;

import com.olvera.shopverseproducto.config.LogProducer;
import com.olvera.shopverseproducto.dto.LogEventDto;
import com.olvera.shopverseproducto.dto.PageResponse;
import com.olvera.shopverseproducto.dto.ProductResponseDto;
import com.olvera.shopverseproducto.exception.ResourceAlreadyExist;
import com.olvera.shopverseproducto.exception.ResourceNotFound;
import com.olvera.shopverseproducto.model.Product;
import com.olvera.shopverseproducto.repository.ProductRepository;
import com.olvera.shopverseproducto.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest extends AbstractServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private LogProducer logProducer;

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

    @Test
    void getProductByCategory_WhenCategoryDoesNotExists() {
        category = "GROCERIES";
        pageNo = 0;
        pageSize = 10;

        List<Product> emptyProductList = List.of();
        Page<Product> emptyProductPage = new PageImpl<>(emptyProductList, PageRequest.of(pageNo, pageSize), 0);

        when(productRepository.findByCategory(eq(category), any(Pageable.class)))
                .thenReturn(emptyProductPage);

        PageResponse pageResponse = productService.getProductsByCategory(category, pageNo, pageSize);

        assertEquals(0, pageResponse.getContent().size());
        assertEquals(0, pageResponse.getTotalElements());
        assertEquals(0, pageResponse.getTotalPages());
        assertTrue(pageResponse.isLast());
    }

    @Test
    void getProductByCategory_WhenThereAreMultiplePages() {
        pageNo = 0;
        pageSize = 1;

        List<Product> productList = List.of(product);
        Page<Product> productPage = new PageImpl<>(productList, PageRequest.of(pageNo, pageSize), 1);

        when(productRepository.findByCategory(eq(category), any(Pageable.class)))
                .thenReturn(productPage);

        PageResponse pageResponse = productService.getProductsByCategory(category, pageNo, pageSize);

        assertEquals(1, pageResponse.getContent().size());
        assertEquals(1, pageResponse.getTotalElements());
        assertEquals(1, pageResponse.getTotalPages());
        assertTrue(pageResponse.isLast());
    }

    @Test
    void updateProduct_ShouldUpdateSuccessfully_WhenProductExists() {
        String productId = "12345";
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponseDto responseDto = productService.updateProduct(productId, productRequestDto);

        assertEquals("Product was updating successfully!!", responseDto.getStatusMsg());
        verify(productRepository).findById(productId);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_ShouldThrowResourceNotFound_WhenProductDoesNotExist() {
        String productId = "12345";
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> productService.updateProduct(productId, productRequestDto));

        verify(productRepository).findById(productId);
        verify(productRepository, never()).save(any());
    }

    @Test
    void deactivateProduct_ShouldDeactivateSuccessfully_WhenProductExists() {

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponseDto responseDto = productService.deactivateProduct(productId);

        assertEquals("Product was deactivated successfully!!", responseDto.getStatusMsg());
        verify(productRepository).findById(productId);
        verify(productRepository).save(any(Product.class));
        verify(logProducer).sendLog(any(LogEventDto.class));
    }

    @Test
    void deactivateProduct_ShouldThrowResourceNotFound_WhenProductDoesNotExist() {

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> productService.deactivateProduct(productId));

        verify(productRepository).findById(productId);
        verify(productRepository, never()).save(any());
    }
}
