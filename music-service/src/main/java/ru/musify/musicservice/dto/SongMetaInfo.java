package ru.musify.musicservice.dto;

import lombok.Builder;

/**
 * Data transfer object (DTO) for representing metadata of a song.
 */
@Builder
public record SongMetaInfo(String title, String author, String audioUrl, String imageUrl) {

}