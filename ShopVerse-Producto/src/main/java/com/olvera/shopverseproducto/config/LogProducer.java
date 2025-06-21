package com.olvera.shopverseproducto.config;

import com.olvera.shopverseproducto.dto.LogEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogProducer {

    private final KafkaTemplate<String, LogEventDto> kafkaTemplate;
    private static final String TOPIC = "shopverse-logs";

    public void sendLog(LogEventDto logEventDto) {
        log.info("Producing log event: {}", logEventDto);
        kafkaTemplate.send(TOPIC, logEventDto);
    }

}
