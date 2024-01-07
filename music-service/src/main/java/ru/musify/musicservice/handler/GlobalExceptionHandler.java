package ru.musify.musicservice.handler;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.musify.musicservice.handler.exception.EntityNotFoundException;
import ru.musify.musicservice.handler.exception.UserNotExistException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseData entityNotFoundException(EntityNotFoundException e) {
    return ResponseData.builder()
        .statusCode(SC_NOT_FOUND)
        .message(e.getMessage())
        .build();
  }

  @ExceptionHandler(UserNotExistException.class)
  public ResponseData userNotExistException(UserNotExistException e) {
    return ResponseData.builder()
        .statusCode(SC_BAD_REQUEST)
        .message(e.getMessage())
        .build();
  }
}
