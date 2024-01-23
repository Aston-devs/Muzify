package ru.musify.playerservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

/**
 * This class represents the configuration for Amazon S3 in the application.
 */
@Configuration
public class S3Config {
    /**
     * The endpoint URL for the Amazon S3 client.
     */
    @Value("${s3.endpointUrl}")
    private String s3EndpointUrl;

    /**
     * The access key for the Amazon S3 client.
     */
    @Value("${s3.accessKey}")
    private String s3AccessKey;

    /**
     * The secret key for the Amazon S3 client.
     */
    @Value("${s3.secretKey}")
    private String s3SecretKey;

    /**
     * Creates and configures an Amazon S3 client.
     * @return An Amazon S3 client configured with the specified endpoint URL, access key, and secret key.
     */
    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(s3AccessKey, s3SecretKey);

        return S3Client.builder()
                .region(Region.EU_WEST_1)
                .endpointOverride(URI.create(s3EndpointUrl))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }
}