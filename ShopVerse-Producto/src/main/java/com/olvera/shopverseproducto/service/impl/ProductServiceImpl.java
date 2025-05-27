package com.olvera.shopverseproducto.service.impl;

import com.olvera.shopverseproducto.dto.ProductRequestDto;
import com.olvera.shopverseproducto.dto.ProductResponseDto;
import com.olvera.shopverseproducto.exception.ResourceAlreadyExist;
import com.olvera.shopverseproducto.model.Product;
import com.olvera.shopverseproducto.repository.ProductRepository;
import com.olvera.shopverseproducto.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {

        Product product = productRepository.findByName(requestDto.getName()).orElseThrow(
                () -> {
                    log.error("Error a product exists with same name");
                    return new ResourceAlreadyExist("Product", "name", requestDto.getName());
                }
        );

        product.setCreatedAt(LocalDateTime.now());

        product = Product.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .price(requestDto.getPrice())
                .stock(requestDto.getStock())
                .category(requestDto.getCategory())
                .imageUrl(requestDto.getImageUrl())
                .isActive(requestDto.getIsActive())
                .build();


        productRepository.save(product);
        log.info("Product was saving successfully: {}", product);

        return ProductResponseDto.builder()
                .statusMsg("Product was creating successfully!!")
                .build();
    }
}
