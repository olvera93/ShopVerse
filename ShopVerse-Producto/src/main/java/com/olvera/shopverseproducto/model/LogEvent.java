package com.olvera.shopverseproducto.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "logs")
public class LogEvent {

    @Id
    private String id;

    private String level;
    private String service;
    private String message;
    private LocalDateTime timestamp;
}
