package ru.musify.musicservice.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musify.musicservice.aop.Loggable;
import ru.musify.musicservice.dto.SongDto;
import ru.musify.musicservice.entity.Song;
import ru.musify.musicservice.handler.exception.EntityNotFoundException;
import ru.musify.musicservice.repository.SongRepository;
import ru.musify.musicservice.service.SongService;
import ru.musify.musicservice.util.mapper.SongMapper;

/**
 * SongServiceImpl class providing implementation for SongService.
 */
@Slf4j
@Loggable
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SongServiceImpl implements SongService {

    /**
     * The repository for accessing and managing Song entities in the database.
     */
    private final SongRepository repository;

    /**
     * The mapper for converting Song entities to DTOs and vice versa.
     */
    private final SongMapper songMapper;

    /**
     * Retrieves a song by its ID.
     *
     * @param id The ID of the song to be retrieved.
     * @return The DTO representation of the song.
     * @throws EntityNotFoundException if the song with the specified ID is not found.
     */
    @Override
    @Cacheable(value = "song", key = "#id", condition = "#result != null")
    public SongDto findById(UUID id) {
        Song song = repository.findById(id)
                .orElseThrow(() -> {
                    log.debug("Could not find song with id {}", id);
                    return new EntityNotFoundException("Song not found with id " + id);
                });
        log.info("Song found with id {}", id);

        return songMapper.toDto(song);
    }

    /**
     * Retrieves all songs.
     *
     * @return A list of DTO representations for all songs.
     */
    @Override
    public List<SongDto> findAll() {
        List<Song> allSongs = repository.findAll();
        log.info("Found all songs");

        return allSongs.stream()
                .map(songMapper::toDto)
                .toList();
    }

    /**
     * Retrieves paginated songs.
     *
     * @param page The page number.
     * @param size The size of each page.
     * @return A list of DTO representations of paginated songs.
     */
    @Override
    @Cacheable(value = "songsCache")
    public List<SongDto> findPaginatedSongs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Song> paginatedSongs = repository.findAll(pageable);
        log.info("Returned all paginated songs with page {}, size {}", page, size);

        return paginatedSongs.get()
                .map(songMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Saves a song.
     *
     * @param song The song to be saved.
     * @return The DTO representation of the saved song.
     */
    @Override
    @Transactional
    public SongDto save(Song song) {
        Song savedSong = repository.save(song);
        log.info("Saved song with id {}", savedSong.getId());

        return songMapper.toDto(savedSong);
    }

    /**
     * Updates a song.
     *
     * @param song The song to be updated.
     * @return The DTO representation of the updated song.
     */
    @Override
    @Transactional
    @CachePut(value = "song", key = "#song.id")
    public SongDto update(Song song) {
        Song updatedSong = repository.save(song);
        log.info("Song updated with id {}", updatedSong.getId());

        return songMapper.toDto(updatedSong);
    }

    /**
     * Removes a song by its ID.
     *
     * @param id The ID of the song to be removed.
     */
    @Override
    @Transactional
    @CacheEvict(value = "song", key = "#id")
    public void removeById(UUID id) {
        repository.deleteById(id);

        log.info("Removed song with id {}", id);
    }
}