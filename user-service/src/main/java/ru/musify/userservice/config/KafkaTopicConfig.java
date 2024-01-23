package ru.musify.userservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * This class provides the configuration for Kafka topics.
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * Creates a new Kafka topic for music uploads.
     *
     * @return The Kafka topic for music uploads.
     */
    @Bean
    public NewTopic musicUploadTopic() {
        return TopicBuilder
                .name("user-creator")
                .build();
    }
}