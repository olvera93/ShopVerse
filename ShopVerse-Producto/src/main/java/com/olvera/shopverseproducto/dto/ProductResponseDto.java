package com.olvera.shopverseproducto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    private String statusCode;

    private String statusMsg;

}
