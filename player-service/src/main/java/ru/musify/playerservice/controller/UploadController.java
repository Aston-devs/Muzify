package ru.musify.playerservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.musify.playerservice.dto.SongMetainfo;
import ru.musify.playerservice.dto.SongRequest;
import ru.musify.playerservice.mapper.SongMapper;
import ru.musify.playerservice.service.S3Service;
import ru.musify.playerservice.service.impl.KafkaProducerService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/player")
public class UploadController {
    private final KafkaProducerService producerService;
    private final S3Service s3Service;
    private final SongMapper mapper;

    @PostMapping("/upload")
    public CompletableFuture<ResponseEntity<String>> uploadFile(@ModelAttribute SongRequest request,
                                                                @RequestParam("image") MultipartFile image,
                                                                @RequestParam("audio") MultipartFile audio) {
        SongMetainfo metainfo = mapper.toMetainfo(request);
        metainfo.setUrls();
        return CompletableFuture.supplyAsync(() -> {
            producerService.sendSongMetainfoToTopic(metainfo);
            s3Service.uploadFile(audio, metainfo.getAudioUrl());
            s3Service.uploadFile(image, metainfo.getImageUrl());
            return ResponseEntity.ok("File uploaded successfully");
        });
    }
}
