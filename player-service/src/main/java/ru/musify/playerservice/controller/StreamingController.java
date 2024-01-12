package ru.musify.playerservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.musify.playerservice.service.impl.S3ServiceImpl;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/player")
public class StreamingController {
    private final S3ServiceImpl s3Service;

    @GetMapping(value = "/play/{audioUrl}", produces = "audio/mp3")
    public Mono<Resource> playMusic(@PathVariable String audioUrl,
                                    @RequestHeader String range) {
        log.info("range is bytes : {}", range);
        return s3Service.getFile(audioUrl);
    }
}
