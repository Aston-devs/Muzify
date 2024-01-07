package ru.musify.playerservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class UploadExceptionHandler {
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to the upload file: " + ex.getMessage());
    }

    @ExceptionHandler(ResourceUploadingException.class)
    public ResponseEntity<String> handleIOException(ResourceUploadingException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to the upload file to S3: " + ex.getMessage());
    }

    @ExceptionHandler(ResourceStreamingException.class)
    public ResponseEntity<String> handleIOException(ResourceStreamingException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to the streaming file: " + ex.getMessage());
    }
}
