package com.olvera.shopverseproducto.service.impl;

import com.olvera.shopverseproducto.dto.LogEventDto;
import com.olvera.shopverseproducto.model.LogEvent;
import com.olvera.shopverseproducto.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogConsumer {

    private final LogRepository logEventRepository;

    @KafkaListener(topics = "shopverse-logs", groupId = "log-group", containerFactory = "logEventKafkaListenerContainerFactory")
    public void consume(LogEventDto logEventDto) {
        log.info("Consumed log event: {}", logEventDto);

        // Map DTO a entidad y guarda en DB
        LogEvent logEvent = LogEvent.builder()
                .level(logEventDto.getLevel())
                .message(logEventDto.getMessage())
                .timestamp(logEventDto.getTimestamp())
                .build();

        logEventRepository.save(logEvent);
    }
}