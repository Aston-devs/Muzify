package ru.musify.musicservice.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record UserSongsDto(@NotNull List<SongDto> songs) {

}
