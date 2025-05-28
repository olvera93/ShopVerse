package com.olvera.shopverseproducto.service;

import com.olvera.shopverseproducto.dto.ProductRequestDto;
import com.olvera.shopverseproducto.dto.ProductResponseDto;

public interface IProductService {

    public ProductResponseDto createProduct(ProductRequestDto requestDto);
}
