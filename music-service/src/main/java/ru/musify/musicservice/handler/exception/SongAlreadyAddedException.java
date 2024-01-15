package ru.musify.musicservice.handler.exception;

public class SongAlreadyAddedException extends RuntimeException {

  public SongAlreadyAddedException(String message) {
    super(message);
  }
}
