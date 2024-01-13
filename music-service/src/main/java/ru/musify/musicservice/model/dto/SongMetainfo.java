package ru.musify.musicservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongMetainfo {

    private String title;
    private String author;
    private String audioUrl;
    private String imageUrl;

}
