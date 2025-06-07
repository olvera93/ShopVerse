package com.olvera.shopverseproducto.service.impl;

import com.olvera.shopverseproducto.dto.PageResponse;
import com.olvera.shopverseproducto.dto.ProductByCategoryDto;
import com.olvera.shopverseproducto.dto.ProductRequestDto;
import com.olvera.shopverseproducto.dto.ProductResponseDto;
import com.olvera.shopverseproducto.exception.ResourceAlreadyExist;
import com.olvera.shopverseproducto.exception.ResourceNotFound;
import com.olvera.shopverseproducto.model.Product;
import com.olvera.shopverseproducto.repository.ProductRepository;
import com.olvera.shopverseproducto.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {

        if (productRepository.findByName(requestDto.getName()).isPresent()) {
            log.error("Error: a product exists with same name");
            throw new ResourceAlreadyExist("Product", "name", requestDto.getName());
        }

        Product product = Product.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .price(requestDto.getPrice())
                .stock(requestDto.getStock())
                .category(requestDto.getCategory())
                .imageUrl(requestDto.getImageUrl())
                .isActive(requestDto.getIsActive())
                .build();

        product.setCreatedAt(LocalDateTime.now());

        productRepository.save(product);
        log.info("Product was saving successfully: {}", product);

        return ProductResponseDto.builder()
                .statusMsg("Product was creating successfully!!")
                .build();
    }

    @Override
    public PageResponse getProductsByCategory(String category, int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Product> products;

        if (category == null || category.isBlank()) {
            products = productRepository.findAll(pageable);
        } else {
            products = productRepository.findByCategory(category, pageable);
        }

        List<ProductByCategoryDto> productByCategoryList = products.getContent()
                .stream()
                .map(this::mapToProduct)
                .toList();

        return new PageResponse(
                productByCategoryList,
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.isLast()
        );
    }

    @Override
    public ProductResponseDto updateProduct(String productId, ProductRequestDto requestDto) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("The product with id: " + productId + " was not found"));

        product.setName(requestDto.getName());
        product.setDescription(requestDto.getDescription());
        product.setPrice(requestDto.getPrice());
        product.setStock(requestDto.getStock());
        product.setCategory(requestDto.getCategory());
        product.setImageUrl(requestDto.getImageUrl());
        product.setIsActive(requestDto.getIsActive());
        product.setUpdatedAt(LocalDateTime.now());

        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(product);
        log.info("Product was updating successfully: {}", product);

        return ProductResponseDto.builder()
                .statusMsg("Product was updating successfully!!")
                .build();
    }

    private ProductByCategoryDto mapToProduct(Product product) {
        return ProductByCategoryDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .build();
    }
}
