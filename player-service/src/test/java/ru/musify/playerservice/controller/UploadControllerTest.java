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
import ru.musify.playerservice.service.impl.S3ServiceImpl;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UploadControllerTest {
    @Mock
    private S3ServiceImpl s3Service;
    @InjectMocks
    private UploadController controller;
    @Test
    @DisplayName("test upload file and then file was upload to s3 object storage")
    void testUploadFile() {
        MultipartFile mockFile = new MockMultipartFile(
                "file", "test.txt",
                "text/plain", "Test file content".getBytes());
        String objectKey = "test-object-key";

        CompletableFuture<ResponseEntity<String>> result = controller.uploadFile(mockFile, objectKey);

        CompletableFuture.allOf(result).join();

        assertNotNull(result);
        assertFalse(result.isCompletedExceptionally());
        assertTrue(result.isDone());
        verify(s3Service, times(1)).uploadFile(mockFile, objectKey);
    }
}