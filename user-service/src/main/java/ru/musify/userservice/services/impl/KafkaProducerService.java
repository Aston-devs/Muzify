package ru.musify.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.musify.userservice.dto.UserMetainfo;

import java.util.UUID;

/**
 * This class provides the functionality to produce messages to Kafka topics.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    /**
     * The Kafka template for producing messages.
     */
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Sends the user ID to the "user-creator" topic.
     *
     * @param metainfo The user metadata containing the user ID.
     */
    public void sendUserIdToTopic(UserMetainfo metainfo) {
        kafkaTemplate.send("user-creator", metainfo);
        log.info("Upload user id was successful");
    }
}