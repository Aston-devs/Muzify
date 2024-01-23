package ru.musify.musicservice.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.musify.musicservice.handler.exception.EntityNotFoundException;
import ru.musify.musicservice.handler.exception.SongAlreadyAddedException;
import ru.musify.musicservice.handler.exception.UserNotExistException;

/**
 * GlobalExceptionHandler class to handle exceptions globally in the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles EntityNotFoundException and returns a ResponseData with NOT_FOUND status.
     *
     * @param e The EntityNotFoundException object.
     * @return ResponseData with status code and error message.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseData entityNotFoundException(EntityNotFoundException e) {
        return ResponseData.builder()
                .statusCode(NOT_FOUND.value())
                .message(e.getMessage())
                .build();
    }

    /**
     * Handles UserNotExistException and returns a ResponseData with BAD_REQUEST status.
     *
     * @param e The UserNotExistException object.
     * @return ResponseData with status code and error message.
     */
    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseData userNotExistException(UserNotExistException e) {
        return ResponseData.builder()
                .statusCode(BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
    }

    /**
     * Handles SongAlreadyAddedException and returns a ResponseData with CONFLICT status.
     *
     * @param e The SongAlreadyAddedException object.
     * @return ResponseData with status code and error message.
     */
    @ExceptionHandler(SongAlreadyAddedException.class)
    @ResponseStatus(CONFLICT)
    public ResponseData songAlreadyAddedException(SongAlreadyAddedException e) {
        return ResponseData.builder()
                .statusCode(CONFLICT.value())
                .message(e.getMessage())
                .build();
    }
}