package com.olvera.shopverseproducto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogEventDto {
    private String level;
    private String message;
    private String serviceName;
    private LocalDateTime timestamp;
    private String path;
}
