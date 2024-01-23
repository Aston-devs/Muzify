package ru.musify.playerservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.musify.playerservice.dto.SongMetainfo;

/**
 * This service class provides functionality for producing messages to Kafka topics.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    /**
     * The Kafka template for sending messages to Kafka topics.
     */
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Sends the song metainfo to the "music-upload" Kafka topic.
     * @param songMetainfo The song metainfo to be sent.
     */
    public void sendSongMetainfoToTopic(SongMetainfo songMetainfo) {
        kafkaTemplate.send("music-upload", songMetainfo);
        log.info("Upload metainfo was successful");
    }
}