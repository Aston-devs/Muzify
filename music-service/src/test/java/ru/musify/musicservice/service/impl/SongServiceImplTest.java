package ru.musify.musicservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.musify.musicservice.dto.AuthorDto;
import ru.musify.musicservice.dto.CoverDto;
import ru.musify.musicservice.dto.ImageDto;
import ru.musify.musicservice.dto.SongDto;
import ru.musify.musicservice.entity.Author;
import ru.musify.musicservice.entity.Cover;
import ru.musify.musicservice.entity.Genre;
import ru.musify.musicservice.entity.Image;
import ru.musify.musicservice.entity.Song;
import ru.musify.musicservice.repository.SongRepository;
import ru.musify.musicservice.util.mapper.SongMapper;

@ContextConfiguration(classes = {SongServiceImpl.class})
@ExtendWith(SpringExtension.class)
class SongServiceImplTest {

    private ImageDto expImageDto;
    private Song expSong;
    private Author expAuthor;
    private Cover expCover;
    private CoverDto expCoverDto;

    @MockBean

    private SongMapper songMapper;
    private SongDto expSongDto;

    @MockBean
    private SongRepository songRepository;

    @Autowired
    private SongServiceImpl songServiceImpl;

    @BeforeEach
    void setup() {

        expCover = new Cover();
        expCover.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        expCover.setId(UUID.randomUUID());
        expCover.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        expCover.setUrl("https://example.org/example");

        expCoverDto = CoverDto.builder()
                .url("https://example.org/example")
                .build();

        expSong = new Song();
        expSong.setAuthor(expAuthor);
        expSong.setCover(expCover);
        expSong.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        expSong.setGenre(Genre.CLASSICAL);
        expSong.setId(UUID.randomUUID());
        expSong.setReleaseDate(LocalDate.of(2020, 1, 1).atStartOfDay());
        expSong.setTitle("Dr");
        expSong.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        expSong.setUrl("https://example.org/example");
        expSong.setUsers(new ArrayList<>());

        expSongDto = SongDto.builder()
                .id(UUID.randomUUID())
                .title("Dr")
                .author(
                        AuthorDto.builder()
                                .id(UUID.randomUUID())
                                .name("AuthorName")
                                .genre(Genre.CLASSICAL)
                                .photo(expImageDto)
                                .build()
                )
                .cover(expCoverDto)
                .url("https://example.org")
                .build();


    }
    @Test
    void testFindById() {

        Optional<Song> ofResult = Optional.of(expSong);

        when(songRepository.findById(Mockito.any())).thenReturn(ofResult);
        when(songMapper.toDto(Mockito.any())).thenReturn(expSongDto);

        SongDto actualFindByIdResult = songServiceImpl.findById(UUID.randomUUID());

        verify(songRepository).findById(Mockito.any());
        verify(songMapper).toDto(Mockito.any());
        assertSame(expSongDto, actualFindByIdResult);
    }

    @Test
    void testFindAll() {

        when(songRepository.findAll()).thenReturn(new ArrayList<>());

        List<SongDto> actualFindAllResult = songServiceImpl.findAll();

        verify(songRepository).findAll();
        assertTrue(actualFindAllResult.isEmpty());
    }

    @Test
    void testFindPaginatedSongs() {

        when(songRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));

        List<SongDto> actualFindPaginatedSongsResult = songServiceImpl.findPaginatedSongs(1, 3);

        verify(songRepository).findAll(Mockito.<Pageable>any());
        assertTrue(actualFindPaginatedSongsResult.isEmpty());
    }

    @Test
    void testSave() {

        when(songRepository.save(Mockito.any())).thenReturn(expSong);
        when(songMapper.toDto(Mockito.any())).thenReturn(expSongDto);

        SongDto actualSaveResult = songServiceImpl.save(expSong);

        verify(songRepository).save(Mockito.any());
        verify(songMapper).toDto(Mockito.any());
        assertSame(expSongDto, actualSaveResult);
    }

    @Test
    void testUpdate() {

        when(songRepository.save(Mockito.any())).thenReturn(expSong);
        when(songMapper.toDto(Mockito.any())).thenReturn(expSongDto);

        SongDto actualUpdateResult = songServiceImpl.update(expSong);

        verify(songRepository).save(Mockito.any());
        verify(songMapper).toDto(Mockito.any());
        assertSame(expSongDto, actualUpdateResult);
    }

    @Test
    void testRemoveById() {

        doNothing().when(songRepository).deleteById(Mockito.any());

        songServiceImpl.removeById(UUID.randomUUID());

        verify(songRepository).deleteById(Mockito.any());
    }
}