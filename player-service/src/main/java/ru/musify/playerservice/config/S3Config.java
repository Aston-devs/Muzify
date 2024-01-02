package ru.musify.playerservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class S3Config {
    @Value("${s3.endpointUrl}")
    private String s3EndpointUrl;

    @Value("${s3.accessKey}")
    private String s3AccessKey;

    @Value("${s3.secretKey}")
    private String s3SecretKey;

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
