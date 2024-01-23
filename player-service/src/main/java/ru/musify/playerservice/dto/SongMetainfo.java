package ru.musify.playerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents the metadata of a song, including its title, author, and URLs for audio and image resources.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SongMetainfo {
    private String title;
    private String author;
    private String audioUrl;
    private String imageUrl;

    /**
     * Generates a unique URL based on the title and author of the song.
     * @return A unique URL generated from the hash codes of the title and author.
     */
    private String generateUrl() {
        return String.valueOf(title.hashCode() + author.hashCode());
    }

    /**
     * Sets the audio and image URLs based on the generated unique URL.
     */
    public void setUrls() {
        audioUrl = generateUrl() + ".audio";
        imageUrl = generateUrl() + ".img";
    }
}