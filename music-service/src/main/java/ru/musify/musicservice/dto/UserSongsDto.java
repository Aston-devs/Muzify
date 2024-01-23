package ru.musify.musicservice.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

import lombok.Builder;

/**
 * Data transfer object (DTO) for representing a user's songs.
 */
@Builder
public record UserSongsDto(@NotNull List<SongDto> songs) {

}
