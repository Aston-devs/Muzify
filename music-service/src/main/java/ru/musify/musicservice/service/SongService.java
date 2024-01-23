package ru.musify.musicservice.service;

import java.util.List;

import ru.musify.musicservice.dto.SongDto;
import ru.musify.musicservice.entity.Song;

/**
 * SongService interface for providing service methods related to Song entities.
 */
public interface SongService extends BaseService<Song, SongDto> {

    /**
     * Retrieves paginated songs.
     *
     * @param page The page number.
     * @param size The size of each page.
     * @return A list of DTO representations of paginated songs.
     */
    List<SongDto> findPaginatedSongs(int page, int size);
}