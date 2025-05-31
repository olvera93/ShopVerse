package com.olvera.shopverseproducto.service;

import com.olvera.shopverseproducto.dto.PageResponse;
import com.olvera.shopverseproducto.dto.ProductRequestDto;
import com.olvera.shopverseproducto.dto.ProductResponseDto;
import org.springframework.data.domain.Pageable;

public interface IProductService {

    ProductResponseDto createProduct(ProductRequestDto requestDto);

    PageResponse getProductsByCategory(String category, int pageNo, int pageSize);

}
