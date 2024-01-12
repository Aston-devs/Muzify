package ru.musify.playerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongMetainfo {
    private String title;
    private String author;
    private String audioUrl;
    private String imageUrl;

    public SongMetainfo(String title, String author) {
        audioUrl = generateUrl(title, author) + ".audio";
        imageUrl = generateUrl(title, author) + ".img";
    }

    private String generateUrl(String title, String author) {
        return String.valueOf(title.hashCode() + author.hashCode());
    }
}
