package ru.musify.playerservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.musify.playerservice.service.impl.S3ServiceImpl;

/**
 * This class represents the controller for streaming audio content in the application.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/player")
public class StreamingController {
    private final S3ServiceImpl s3Service;

    /**
     * Retrieves and streams the audio content for playback.
     * @param audioUrl The URL of the audio to be played.
     * @param range The range of bytes for streaming the audio.
     * @return A Mono emitting the resource representing the audio content.
     */
    @GetMapping(value = "/play/{audioUrl}", produces = "audio/mp3")
    public Mono<Resource> playMusic(@PathVariable String audioUrl,
                                    @RequestHeader String range) {
        log.info("range is bytes : {}", range);
        return s3Service.getFile(audioUrl);
    }
}