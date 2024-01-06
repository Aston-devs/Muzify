package ru.musify.musicservice.model.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AddSongToUserDto(@NotNull UUID userId, @NotNull UUID songId) {

}
