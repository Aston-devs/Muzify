package ru.musify.playerservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

import static jakarta.servlet.RequestDispatcher.ERROR_MESSAGE;

@Service
public class S3ServiceImpl implements S3Service {
    @Value("${s3.bucketName}")
    private String bucketName;
    private final S3Client s3Client;
    private static final Logger LOG = LoggerFactory.getLogger(S3ServiceImpl.class);

    @Autowired
    public S3ServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void uploadFile(MultipartFile file, String objectKey) throws IOException {
        byte[] contentBytes = file.getBytes();
        CompletableFuture.runAsync(() -> {
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build(), RequestBody.fromBytes(contentBytes));
        }).thenRun(() -> {
            LOG.info("Upload was successful");
        }).exceptionally(ex -> {
            ExceptionThrower.handleUploadFailure(
                    new ResourceUploadingException(ERROR_MESSAGE),
                    ERROR_MESSAGE);
            return null;
        });
    }

    @Override
    public byte[] getFile(String objectKey) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        ResponseBytes<GetObjectResponse> responseBytes = s3Client
                .getObject(getObjectRequest, ResponseTransformer.toBytes());

        return responseBytes.asByteArray();
    }
}
