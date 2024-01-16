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
    @MockBean
    private SongMapper songMapper;

    @MockBean
    private SongRepository songRepository;

    @Autowired
    private SongServiceImpl songServiceImpl;

    @Test
    void testFindById() {

        Image photo = new Image();
        photo.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        photo.setId(UUID.randomUUID());
        photo.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        photo.setUrl("https://example.org/example");

        Author author = new Author();
        author.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        author.setGenre(Genre.CLASSICAL);
        author.setId(UUID.randomUUID());
        author.setName("Name");
        author.setPhoto(photo);
        author.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());

        Cover cover = new Cover();
        cover.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        cover.setId(UUID.randomUUID());
        cover.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        cover.setUrl("https://example.org/example");

        Song song = new Song();
        song.setAuthor(author);
        song.setCover(cover);
        song.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        song.setGenre(Genre.CLASSICAL);
        song.setId(UUID.randomUUID());
        song.setReleaseDate(LocalDate.of(2020, 1, 1).atStartOfDay());
        song.setTitle("Dr");
        song.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        song.setUrl("https://example.org/example");
        song.setUsers(new ArrayList<>());
        Optional<Song> ofResult = Optional.of(song);
        when(songRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        UUID id = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        AuthorDto author2 = new AuthorDto(id2, "Name", Genre.CLASSICAL,
                new ImageDto(UUID.randomUUID(), "https://example.org/example"));

        SongDto songDto = new SongDto(id, "Dr", author2, new CoverDto("https://example.org/example"),
                "https://example.org/example");

        when(songMapper.toDto(Mockito.<Song>any())).thenReturn(songDto);

        SongDto actualFindByIdResult = songServiceImpl.findById(UUID.randomUUID());

        verify(songRepository).findById(Mockito.<UUID>any());
        verify(songMapper).toDto(Mockito.<Song>any());
        assertSame(songDto, actualFindByIdResult);
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

        Image photo = new Image();
        photo.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        photo.setId(UUID.randomUUID());
        photo.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        photo.setUrl("https://example.org/example");

        Author author = new Author();
        author.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        author.setGenre(Genre.CLASSICAL);
        author.setId(UUID.randomUUID());
        author.setName("Name");
        author.setPhoto(photo);
        author.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());

        Cover cover = new Cover();
        cover.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        cover.setId(UUID.randomUUID());
        cover.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        cover.setUrl("https://example.org/example");

        Song song = new Song();
        song.setAuthor(author);
        song.setCover(cover);
        song.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        song.setGenre(Genre.CLASSICAL);
        song.setId(UUID.randomUUID());
        song.setReleaseDate(LocalDate.of(2020, 1, 1).atStartOfDay());
        song.setTitle("Dr");
        song.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        song.setUrl("https://example.org/example");
        song.setUsers(new ArrayList<>());
        when(songRepository.save(Mockito.<Song>any())).thenReturn(song);
        UUID id = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        AuthorDto author2 = new AuthorDto(id2, "Name", Genre.CLASSICAL,
                new ImageDto(UUID.randomUUID(), "https://example.org/example"));

        SongDto songDto = new SongDto(id, "Dr", author2, new CoverDto("https://example.org/example"),
                "https://example.org/example");

        when(songMapper.toDto(Mockito.<Song>any())).thenReturn(songDto);

        Image photo2 = new Image();
        photo2.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        photo2.setId(UUID.randomUUID());
        photo2.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        photo2.setUrl("https://example.org/example");

        Author author3 = new Author();
        author3.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        author3.setGenre(Genre.CLASSICAL);
        author3.setId(UUID.randomUUID());
        author3.setName("Name");
        author3.setPhoto(photo2);
        author3.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());

        Cover cover2 = new Cover();
        cover2.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        cover2.setId(UUID.randomUUID());
        cover2.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        cover2.setUrl("https://example.org/example");

        Song song2 = new Song();
        song2.setAuthor(author3);
        song2.setCover(cover2);
        song2.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        song2.setGenre(Genre.CLASSICAL);
        song2.setId(UUID.randomUUID());
        song2.setReleaseDate(LocalDate.of(2020, 1, 1).atStartOfDay());
        song2.setTitle("Dr");
        song2.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        song2.setUrl("https://example.org/example");
        song2.setUsers(new ArrayList<>());

        SongDto actualSaveResult = songServiceImpl.save(song2);

        verify(songRepository).save(Mockito.<Song>any());
        verify(songMapper).toDto(Mockito.<Song>any());
        assertSame(songDto, actualSaveResult);
    }

    @Test
    void testUpdate() {

        Image photo = new Image();
        photo.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        photo.setId(UUID.randomUUID());
        photo.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        photo.setUrl("https://example.org/example");

        Author author = new Author();
        author.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        author.setGenre(Genre.CLASSICAL);
        author.setId(UUID.randomUUID());
        author.setName("Name");
        author.setPhoto(photo);
        author.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());

        Cover cover = new Cover();
        cover.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        cover.setId(UUID.randomUUID());
        cover.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        cover.setUrl("https://example.org/example");

        Song song = new Song();
        song.setAuthor(author);
        song.setCover(cover);
        song.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        song.setGenre(Genre.CLASSICAL);
        song.setId(UUID.randomUUID());
        song.setReleaseDate(LocalDate.of(2020, 1, 1).atStartOfDay());
        song.setTitle("Dr");
        song.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        song.setUrl("https://example.org/example");
        song.setUsers(new ArrayList<>());
        when(songRepository.save(Mockito.<Song>any())).thenReturn(song);
        UUID id = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        AuthorDto author2 = new AuthorDto(id2, "Name", Genre.CLASSICAL,
                new ImageDto(UUID.randomUUID(), "https://example.org/example"));

        SongDto songDto = new SongDto(id, "Dr", author2, new CoverDto("https://example.org/example"),
                "https://example.org/example");

        when(songMapper.toDto(Mockito.<Song>any())).thenReturn(songDto);

        Image photo2 = new Image();
        photo2.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        photo2.setId(UUID.randomUUID());
        photo2.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        photo2.setUrl("https://example.org/example");

        Author author3 = new Author();
        author3.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        author3.setGenre(Genre.CLASSICAL);
        author3.setId(UUID.randomUUID());
        author3.setName("Name");
        author3.setPhoto(photo2);
        author3.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());

        Cover cover2 = new Cover();
        cover2.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        cover2.setId(UUID.randomUUID());
        cover2.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        cover2.setUrl("https://example.org/example");

        Song song2 = new Song();
        song2.setAuthor(author3);
        song2.setCover(cover2);
        song2.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        song2.setGenre(Genre.CLASSICAL);
        song2.setId(UUID.randomUUID());
        song2.setReleaseDate(LocalDate.of(2020, 1, 1).atStartOfDay());
        song2.setTitle("Dr");
        song2.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        song2.setUrl("https://example.org/example");
        song2.setUsers(new ArrayList<>());

        SongDto actualUpdateResult = songServiceImpl.update(song2);

        verify(songRepository).save(Mockito.<Song>any());
        verify(songMapper).toDto(Mockito.<Song>any());
        assertSame(songDto, actualUpdateResult);
    }

    @Test
    void testRemoveById() {

        doNothing().when(songRepository).deleteById(Mockito.<UUID>any());

        songServiceImpl.removeById(UUID.randomUUID());

        verify(songRepository).deleteById(Mockito.<UUID>any());
    }
}