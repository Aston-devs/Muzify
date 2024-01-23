package ru.musify.playerservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;
import ru.musify.playerservice.service.impl.S3ServiceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StreamingControllerTest {
    @Mock
    private S3ServiceImpl s3Service;
    @InjectMocks
    private StreamingController controller;
    @Test
    @DisplayName("test play music and then returned resource")
    void testPlayMusic() {
        String objectKey = "test-object-key";
        String range = "bytes=0-100";

        Resource mockResource = mock();
        when(s3Service.getFile(objectKey)).thenReturn(Mono.just(mockResource));

        Mono<Resource> result = controller.playMusic(objectKey, range);

        assertNotNull(result);
        assertSame(mockResource, result.block());
        verify(s3Service, times(1)).getFile(objectKey);
    }
}