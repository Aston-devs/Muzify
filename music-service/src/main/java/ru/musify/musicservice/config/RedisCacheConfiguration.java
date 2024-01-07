package ru.musify.musicservice.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisCacheConfiguration {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        // RedisCacheWriter - избегает блокировок, позволяет улучшить производительность
        // и параллельную обработку операций с кэшем
        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);

        org.springframework.data.redis.cache.RedisCacheConfiguration configuration = org.springframework.data.redis.cache.RedisCacheConfiguration
                .defaultCacheConfig() // Получение конфигурации кэша по умолчанию
                .entryTtl(Duration.ofMinutes(10)) // Установка времени жизни записей в кэше (10 минут)
                .serializeValuesWith(
                        RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer())
                );

        return RedisCacheManager.builder(cacheWriter)
                .cacheDefaults(configuration)
                .build();
    }
}