package ru.musify.playerservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * This class represents the configuration for Kafka topics in the application.
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * Creates a new Kafka topic for music uploads.
     * @return A new Kafka topic for music uploads.
     */
    @Bean
    public NewTopic musicUploadTopic() {
        return TopicBuilder
                .name("music-upload")
                .build();
    }
}