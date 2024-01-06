package ru.musify.musicservice.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.musify.musicservice.model.entity.Genre;

public record NewSongDto(@NotNull @Size(min = 2, max = 150) String author,
                         @NotNull @Size(min = 2, max = 150) String name, @NotNull String songUrl,
                         String coverUrl, Genre genre) {

}
