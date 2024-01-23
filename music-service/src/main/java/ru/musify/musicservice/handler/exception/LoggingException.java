package ru.musify.musicservice.handler.exception;

/**
 * Exception indicating a failure in logging operations.
 */
public class LoggingException extends RuntimeException {

    /**
     * Constructs a new logging exception with the specified detail message.
     *
     * @param message the detail message
     */
    public LoggingException(String message) {
        super(message);
    }
}