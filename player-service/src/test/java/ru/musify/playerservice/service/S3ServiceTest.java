package ru.musify.playerservice.service;

import org.junit.jupiter.api.BeforeEach;
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
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class S3ServiceTest {
    @Mock
    private S3Client s3Client;
    @InjectMocks
    private S3ServiceImpl s3Service;
    private String objectKey;
    private MultipartFile file;
    private byte[] expectedContent;
    @BeforeEach
    void setUp() {
        file = new MockMultipartFile("test-file",
                "test-file.txt",
                "text/plain",
                "Test file content".getBytes());
        objectKey = "test-object-key";
        expectedContent = "some content".getBytes();
    }

    @Test
    @DisplayName("upload file when was successful")
    void test_uploadFile_when_success() throws IOException {
        assertDoesNotThrow(() -> s3Service.uploadFile(file, objectKey));
    }

//    @Test
//    @DisplayName("get file from S3 storage")
//    void test_GetFile() {
//        GetObjectRequest getObjectRequest = GetObjectRequest.builder().key(objectKey).build();
//        GetObjectResponse getObjectResponse = GetObjectResponse.builder().build();
//        ResponseBytes<GetObjectResponse> responseBytes = ResponseBytes
//                .fromByteArray(getObjectResponse, expectedContent);
//
//        when(s3Client.getObject(getObjectRequest, ResponseTransformer.toBytes()))
//                .thenReturn(responseBytes);
//        byte[] actualContent = s3Service.getFile(objectKey);
//
//        assertArrayEquals(expectedContent, actualContent);
//        verify(s3Client).getObject(eq(getObjectRequest), eq(ResponseTransformer.toBytes()));
//    }
}