package ru.musify.musicservice.dto;

import lombok.Builder;

@Builder
public record SongMetaInfo(String title, String author, String audioUrl, String imageUrl) {

}
