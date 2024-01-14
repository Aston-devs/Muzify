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

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  public ResponseData entityNotFoundException(EntityNotFoundException e) {
    return ResponseData.builder()
        .statusCode(NOT_FOUND.value())
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(UserNotExistException.class)
  @ResponseStatus(BAD_REQUEST)
  public ResponseData userNotExistException(UserNotExistException e) {
    return ResponseData.builder()
        .statusCode(BAD_REQUEST.value())
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(SongAlreadyAddedException.class)
  @ResponseStatus(CONFLICT)
  public ResponseData songAlreadyAddedException(SongAlreadyAddedException e) {
    return ResponseData.builder()
        .statusCode(CONFLICT.value())
        .message(e.getMessage())
        .build();
  }
}
