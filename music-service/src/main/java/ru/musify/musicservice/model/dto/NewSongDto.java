package ru.musify.musicservice.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;
import ru.musify.musicservice.model.entity.Genre;

@Builder
public record NewSongDto(@NotNull @Size(min = 2, max = 150) String author,
                         @NotNull @Size(min = 2, max = 150) String title, @URL String songUrl,
                         String coverUrl, Genre genre) {

}
