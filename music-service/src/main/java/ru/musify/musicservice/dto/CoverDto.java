package ru.musify.musicservice.dto;


import java.io.Serializable;

import lombok.Builder;

@Builder
public record CoverDto(String url) implements Serializable {
}