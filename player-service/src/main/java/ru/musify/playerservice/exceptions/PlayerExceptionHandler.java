package ru.musify.playerservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

/**
 * This class provides global exception handling for the player-related operations in the application.
 */
@ControllerAdvice
public class PlayerExceptionHandler {

    /**
     * Handles IOException and returns an appropriate response entity.
     * @param ex The IOException that was thrown.
     * @return A ResponseEntity with an error message and status code.
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to the upload file: " + ex.getMessage());
    }

    /**
     * Handles ResourceUploadingException and returns an appropriate response entity.
     * @param ex The ResourceUploadingException that was thrown.
     * @return A ResponseEntity with an error message and status code.
     */
    @ExceptionHandler(ResourceUploadingException.class)
    public ResponseEntity<String> handleResourceUploadingException(ResourceUploadingException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to the upload file to S3: " + ex.getMessage());
    }

    /**
     * Handles ResourceStreamingException and returns an appropriate response entity.
     * @param ex The ResourceStreamingException that was thrown.
     * @return A ResponseEntity with an error message and status code.
     */
    @ExceptionHandler(ResourceStreamingException.class)
    public ResponseEntity<String> handleResourceStreamingException(ResourceStreamingException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to the streaming file: " + ex.getMessage());
    }
}