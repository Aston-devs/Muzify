package ru.musify.musicservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;
import ru.musify.musicservice.entity.Genre;

@Builder
public record AuthorDto(@NotNull UUID id,
                        @NotNull @Size(min = 2, max = 150) @NotEmpty @NotBlank String name,
                        @NotNull Genre genre,
                        ImageDto photo) implements
    Serializable {

}