package ru.musify.playerservice.exceptions;

/**
 * This exception is thrown to indicate an error during the streaming of a resource.
 */
public class ResourceStreamingException extends RuntimeException {

    /**
     * Constructs a new resource streaming exception with the specified detail message.
     * @param message The detail message.
     */
    public ResourceStreamingException(String message) {
        super(message);
    }
}