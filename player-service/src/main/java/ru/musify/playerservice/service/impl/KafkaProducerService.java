package ru.musify.playerservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.musify.playerservice.dto.SongMetainfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendSongMetainfoToTopic(SongMetainfo songMetainfo) {
        kafkaTemplate.send("music-upload", songMetainfo);
        log.info("Upload metainfo was successful");
    }
}
