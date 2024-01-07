package ru.musify.musicservice.service;

import java.util.List;
import ru.musify.musicservice.model.dto.SongDto;
import ru.musify.musicservice.model.entity.Song;

public interface SongService extends BaseService<Song, SongDto> {

  List<SongDto> findPaginatedSongs(int page, int size);
}
