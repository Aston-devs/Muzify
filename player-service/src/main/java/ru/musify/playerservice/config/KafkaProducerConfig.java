package ru.musify.playerservice.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

/**
 * This class represents the configuration for a Kafka producer in the application.
 */
@Configuration
public class KafkaProducerConfig {

    /**
     * The bootstrap servers for the Kafka producer.
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Returns the configuration for the Kafka producer.
     * @return A map containing the producer configuration properties.
     */
    public Map<String, Object> producerConfig() {
        return Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class
        );
    }

    /**
     * Creates a producer factory based on the producer configuration.
     * @return A producer factory for creating Kafka producers.
     */
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    /**
     * Creates a Kafka template for sending messages to Kafka topics.
     * @param producerFactory The producer factory to be used by the Kafka template.
     * @return A Kafka template for sending messages to Kafka topics.
     */
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(
            ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}