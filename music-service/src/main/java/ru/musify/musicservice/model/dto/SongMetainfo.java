package ru.musify.musicservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SongMetainfo {

    private String title;
    private String author;
    private String audioUrl;
    private String imageUrl;

    public SongMetainfo() {

        audioUrl = generateUrl() + ".audio";
        imageUrl = generateUrl() + ".img";

    }

    private String generateUrl() {
        return String.valueOf(this.title.hashCode() + this.author.hashCode());
    }
}
