package ru.musify.playerservice.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface S3Service {
    void uploadFile(MultipartFile file, String objectKey);
    Mono<Resource> getFile(String objectKey);
}
