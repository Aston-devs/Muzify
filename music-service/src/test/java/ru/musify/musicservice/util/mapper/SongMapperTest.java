package ru.musify.musicservice.util.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;;
import ru.musify.musicservice.dto.AuthorDto;
import ru.musify.musicservice.dto.CoverDto;
import ru.musify.musicservice.dto.ImageDto;
import ru.musify.musicservice.dto.SongDto;
import ru.musify.musicservice.entity.Author;
import ru.musify.musicservice.entity.Cover;
import ru.musify.musicservice.entity.Genre;
import ru.musify.musicservice.entity.Song;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SongMapperTest {

    private ImageDto expImageDto;
    private Song expSong;
    private SongDto expSongDto;
    private Author expAuthor;
    private Cover expCover;
    private CoverDto expCoverDto;

    @Mock
    private  SongMapper songMapper;

    @BeforeEach
    void setUp() {

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
    void testToEntity() {

        when(songMapper.toEntity(expSongDto)).thenReturn(expSong);
        Song actual = songMapper.toEntity(expSongDto);
        assertEquals(expSong, actual);

    }

    @Test
    void testToDto() {

        when(songMapper.toDto(expSong)).thenReturn(expSongDto);
        SongDto actual = songMapper.toDto(expSong);
        assertEquals(expSongDto, actual);

    }
}
