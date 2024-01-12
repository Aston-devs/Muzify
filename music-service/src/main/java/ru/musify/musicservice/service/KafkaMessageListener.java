package ru.musify.musicservice.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.musify.musicservice.model.dto.SongMetainfo;

import java.time.LocalDateTime;


@Component
public class KafkaMessageListener {
    @KafkaListener(topics = "topic-upload", groupId = "my_group_id")
    public void listen(ConsumerRecord<String, SongMetainfo> record) {
        SongMetainfo song = record.value();
        System.out.println(
                "Received Message: " + song.getTitle() +
                " by " + song.getAuthor() +
                " " + LocalDateTime.now());
    }
}

//    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerConfig.class);
//
//    @KafkaListener(topics = "topicMS", groupId = "my_group_id")
//    public void listen(ConsumerRecord<String, Object> record) {
//        LOGGER.info("Received message: key={}, value={}", record.key(), record.value());
//
//    }
//}

