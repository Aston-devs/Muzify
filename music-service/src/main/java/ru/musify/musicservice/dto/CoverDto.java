package ru.musify.musicservice.dto;


import java.io.Serializable;

import lombok.Builder;

/**
 * Data transfer object (DTO) for representing a cover.
 */
@Builder
public record CoverDto(String url) implements Serializable {

}