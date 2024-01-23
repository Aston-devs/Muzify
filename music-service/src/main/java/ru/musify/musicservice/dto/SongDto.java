package ru.musify.musicservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;

/**
 * Data transfer object (DTO) for representing a song.
 */
@Builder
public record SongDto(@NotNull UUID id, @NotNull @NotBlank String title, AuthorDto author,
                      CoverDto cover, String url) implements Serializable {

}