package ru.musify.musicservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;
import org.hibernate.validator.constraints.URL;


@Builder
public record SongDto(@NotNull UUID id, @NotNull @NotBlank String title, AuthorDto author,
                      CoverDto cover, @URL String url) implements Serializable {
}