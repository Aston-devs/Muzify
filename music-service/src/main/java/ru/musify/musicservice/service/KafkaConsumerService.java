package ru.musify.musicservice.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "music-upload")
    public void consume(ConsumerRecord<String, Object> record) {
        LOGGER.info("Received message: key={}, value={}", record.key(), record.value());

    }
}
