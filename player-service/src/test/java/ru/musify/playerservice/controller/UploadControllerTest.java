package ru.musify.playerservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.musify.playerservice.dto.SongMetainfo;
import ru.musify.playerservice.dto.SongRequest;
import ru.musify.playerservice.mapper.SongMapper;
import ru.musify.playerservice.service.impl.KafkaProducerService;
import ru.musify.playerservice.service.impl.S3ServiceImpl;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UploadControllerTest {
    @Mock
    private S3ServiceImpl s3Service;
    @Mock
    private SongMapper mapper;
    @Mock
    private KafkaProducerService kafkaProducerService;
    @InjectMocks
    private UploadController controller;
    @Test
    @DisplayName("test upload file and then file was upload to s3 object storage")
    void testUploadFile() {
        SongRequest request = new SongRequest("title", "author");
        MultipartFile audioMockFile = new MockMultipartFile("audio", "foo.mp3".getBytes());
        MultipartFile imgMockFile = new MockMultipartFile("audio", "foo.mp3".getBytes());
        SongMetainfo metainfo = new SongMetainfo(request.title(), request.author());
        when(mapper.toMetainfo(request)).thenReturn(metainfo);

        CompletableFuture<ResponseEntity<String>> result = controller.uploadFile(request, audioMockFile, imgMockFile);

        CompletableFuture.allOf(result).join();

        assertNotNull(result);
        assertFalse(result.isCompletedExceptionally());
        assertTrue(result.isDone());
    }
}