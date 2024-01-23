package ru.musify.musicservice.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.Builder;

/**
 * Data transfer object (DTO) for representing a user.
 */
@Builder
public record UserDto(@NotNull UUID id, List<SongDto> userSongs) implements Serializable {

}