package ru.musify.musicservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;
import ru.musify.musicservice.model.entity.Genre;


@Builder
public record SongDto(@NotNull UUID id, @NotNull @NotBlank String title, AuthorDto author,
                      CoverDto cover, @NotNull Time duration, Genre genre, String album,
                      @URL String url, LocalDateTime releaseDate) implements Serializable {

}