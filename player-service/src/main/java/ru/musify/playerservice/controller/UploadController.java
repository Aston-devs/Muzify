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

/**
 * This class represents the controller for handling file uploads by administrators in the application.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class UploadController {
    /**
     * Service for producing messages to Kafka topics.
     */
    private final KafkaProducerService producerService;

    /**
     * Service for interacting with Amazon S3.
     */
    private final S3Service s3Service;

    /**
     * Mapper for mapping song request to song metadata.
     */
    private final SongMapper mapper;

    /**
     * Handles the upload of a song file along with its metadata by administrators.
     * @param request The request containing the song metadata.
     * @param image The image file associated with the song.
     * @param audio The audio file to be uploaded.
     * @return A CompletableFuture that will complete with a ResponseEntity indicating the result of the upload.
     */
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