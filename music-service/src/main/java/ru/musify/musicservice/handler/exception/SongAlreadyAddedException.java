package ru.musify.musicservice.handler.exception;

/**
 * Exception indicating that a song is already added.
 */
public class SongAlreadyAddedException extends RuntimeException {

    /**
     * Constructs a new SongAlreadyAddedException with the specified detail message.
     *
     * @param message the detail message
     */
    public SongAlreadyAddedException(String message) {
        super(message);
    }
}