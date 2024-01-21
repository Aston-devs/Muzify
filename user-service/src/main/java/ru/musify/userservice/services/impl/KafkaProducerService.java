package ru.musify.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.musify.userservice.dto.UserMetainfo;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendUserIdToTopic(UserMetainfo metainfo) {
        kafkaTemplate.send("user-creator", metainfo);
        log.info("Upload user id was successful");
    }
}
