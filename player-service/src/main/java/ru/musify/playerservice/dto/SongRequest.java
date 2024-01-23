package ru.musify.playerservice.dto;

/**
 * This record represents a request for a song, including its title and author.
 */
public record SongRequest(String title, String author) {
}