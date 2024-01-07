package ru.musify.playerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.musify.playerservice.service.S3Service;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/player")
public class UploadController {
    private final S3Service s3Service;

    @Autowired
    public UploadController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public CompletableFuture<ResponseEntity<String>> uploadFile(@RequestParam("file") MultipartFile file,
                                                                @RequestParam("objectKey") String objectKey) {
        return CompletableFuture.supplyAsync(() -> {
            s3Service.uploadFile(file, objectKey);
            return ResponseEntity.ok("File uploaded successfully");
        });
    }

}
