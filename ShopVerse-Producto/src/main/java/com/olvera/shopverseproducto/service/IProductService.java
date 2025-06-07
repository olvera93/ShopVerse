package com.olvera.shopverseproducto.service;

import com.olvera.shopverseproducto.dto.PageResponse;
import com.olvera.shopverseproducto.dto.ProductRequestDto;
import com.olvera.shopverseproducto.dto.ProductResponseDto;

public interface IProductService {

    ProductResponseDto createProduct(ProductRequestDto requestDto);

    PageResponse getProductsByCategory(String category, int pageNo, int pageSize);

    ProductResponseDto updateProduct(String productId, ProductRequestDto requestDto);

}
