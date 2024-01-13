package ru.musify.musicservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageListener {

    @KafkaListener(topics = "music-upload", groupId = "muzify-song")
    void listen(String data) {
        System.out.println("Listening" + data);
    }
}