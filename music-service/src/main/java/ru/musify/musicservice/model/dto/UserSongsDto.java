package ru.musify.musicservice.model.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UserSongsDto(@NotNull UUID id, @NotNull List<SongDto> songs) {

}
