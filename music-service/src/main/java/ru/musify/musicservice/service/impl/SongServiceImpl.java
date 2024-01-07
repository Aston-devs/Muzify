package ru.musify.musicservice.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musify.musicservice.handler.exception.EntityNotFoundException;
import ru.musify.musicservice.model.dto.SongDto;
import ru.musify.musicservice.model.entity.Song;
import ru.musify.musicservice.repository.SongRepository;
import ru.musify.musicservice.service.SongService;
import ru.musify.musicservice.util.mapper.SongMapper;

@Service
@Slf4j
public class SongServiceImpl implements SongService {

  private final SongRepository repository;

  private final SongMapper songMapper;

  @Autowired
  public SongServiceImpl(SongRepository repository, SongMapper songMapper) {
    this.repository = repository;
    this.songMapper = songMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public SongDto findById(UUID id) {
    Song song = repository.findById(id)
        .orElseThrow(() -> {
          log.debug("Could not find song with id {}", id);
          return new EntityNotFoundException("Song not found with id " + id);
        });
    log.info("Song found with id {}", id);

    return songMapper.toDto(song);
  }

  @Override
  @Transactional(readOnly = true)
  public List<SongDto> findAll() {
    List<Song> allSongs = repository.findAll();
    log.info("Found all songs");

    return allSongs.stream()
        .map(songMapper::toDto)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<SongDto> findPaginatedSongs(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Song> paginatedSongs = repository.findAll(pageable);

    return paginatedSongs.get()
        .map(songMapper::toDto)
        .toList();
  }

  @Override
  @Transactional
  public SongDto save(Song song) {
    Song savedSong = repository.save(song);
    log.info("Saved song with id {}", savedSong.getId());

    return songMapper.toDto(savedSong);
  }

  @Override
  @Transactional
  public SongDto update(Song song) {
    Song updatedSong = repository.save(song);
    log.info("Song updated with id {}", updatedSong.getId());

    return songMapper.toDto(updatedSong);
  }

  @Override
  @Transactional
  public void removeById(UUID id) {
    repository.deleteById(id);

    log.info("Removed song with id {}", id);
  }
}
