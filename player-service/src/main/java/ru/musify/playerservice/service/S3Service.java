package ru.musify.playerservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    void uploadFile(MultipartFile file, String objectKey) throws IOException;
    byte[] getFile(String objectKey);
}
