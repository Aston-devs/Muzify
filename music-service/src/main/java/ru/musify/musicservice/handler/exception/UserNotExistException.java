package ru.musify.musicservice.handler.exception;

/**
 * Exception indicating that a user does not exist.
 */
public class UserNotExistException extends RuntimeException {

    /**
     * Constructs a new UserNotExistException with the specified detail message.
     *
     * @param message the detail message
     */
    public UserNotExistException(String message) {
        super(message);
    }
}