package ru.musify.playerservice.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

/**
 * This interface defines the contract for interacting with Amazon S3.
 */
public interface S3Service {

    /**
     * Uploads a file to the specified object key in the S3 bucket.
     *
     * @param file       The file to be uploaded.
     * @param objectKey  The key under which the file will be stored in the S3 bucket.
     */
    void uploadFile(MultipartFile file, String objectKey);

    /**
     * Retrieves a file from the S3 bucket based on the specified object key.
     *
     * @param objectKey  The key of the file to be retrieved from the S3 bucket.
     * @return           A Mono emitting the resource representing the retrieved file content.
     */
    Mono<Resource> getFile(String objectKey);
}