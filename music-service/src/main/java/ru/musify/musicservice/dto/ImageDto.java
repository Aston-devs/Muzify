package ru.musify.musicservice.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;

/**
 * Data transfer object (DTO) for representing an image.
 */
@Builder
public record ImageDto(@NotNull UUID id,
                       String url) implements Serializable {

}