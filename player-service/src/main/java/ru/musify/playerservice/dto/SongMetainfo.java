package ru.musify.playerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SongMetainfo {
    private String title;
    private String author;
    private String audioUrl;
    private String imageUrl;

    private String generateUrl() {
        return String.valueOf(title.hashCode() + author.hashCode());
    }

    public void setUrls() {
        audioUrl = generateUrl() + ".audio";
        imageUrl = generateUrl() + ".img";
    }
}
