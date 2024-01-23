package ru.musify.musicservice.handler.exception;

/**
 * Exception indicating that an entity was not found.
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructor with a parameter.
     *
     * @param message error message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}