package ru.musify.playerservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import ru.musify.playerservice.exceptions.ResourceUploadingException;
import ru.musify.playerservice.service.S3Service;
import ru.musify.playerservice.utils.ExceptionThrower;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * This class represents an implementation of the S3Service interface for interacting with Amazon S3.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    /**
     * The name of the S3 bucket to be used for file storage.
     */
    @Value("${s3.bucketName}")
    private String bucketName;

    /**
     * The S3 client used for interacting with Amazon S3.
     */
    private final S3Client s3Client;

    /**
     * Uploads a file to the specified object key in the S3 bucket.
     *
     * @param file       The file to be uploaded.
     * @param objectKey  The key under which the file will be stored in the S3 bucket.
     */
    @Override
    public void uploadFile(MultipartFile file, String objectKey) {
        try {
            byte[] contentBytes = file.getBytes();
            CompletableFuture.runAsync(() -> {
                s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build(), RequestBody.fromBytes(contentBytes));
            }).thenRun(() -> log.info("Upload was successful"));
        } catch (IOException e) {
            ExceptionThrower.handleUploadFailure(
                    new ResourceUploadingException(e.getMessage()));
        }
    }

    /**
     * Retrieves a file from the S3 bucket based on the specified object key.
     *
     * @param objectKey  The key of the file to be retrieved from the S3 bucket.
     * @return           A Mono emitting the resource representing the retrieved file content.
     */
    @Override
    public Mono<Resource> getFile(String objectKey) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        ResponseBytes<GetObjectResponse> responseBytes = s3Client
                .getObject(getObjectRequest, ResponseTransformer.toBytes());

        byte[] content = responseBytes.asByteArray();
        return Mono.just(new ByteArrayResource(content));
    }
}