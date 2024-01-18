package ru.musify.musicservice.listener;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.musify.musicservice.dto.AuthorDto;
import ru.musify.musicservice.dto.CoverDto;
import ru.musify.musicservice.dto.ImageDto;
import ru.musify.musicservice.dto.SongDto;
import ru.musify.musicservice.dto.SongMetaInfo;
import ru.musify.musicservice.entity.Author;
import ru.musify.musicservice.entity.Cover;
import ru.musify.musicservice.entity.Genre;
import ru.musify.musicservice.entity.Image;
import ru.musify.musicservice.entity.Song;
import ru.musify.musicservice.service.AuthorService;
import ru.musify.musicservice.service.CoverService;
import ru.musify.musicservice.service.SongService;

@ContextConfiguration(classes = {KafkaMessageListener.class})
@ExtendWith(SpringExtension.class)
class KafkaMessageListenerTest {
    @MockBean
    private AuthorService authorService;

    @MockBean
    private CoverService coverService;

    @Autowired
    private KafkaMessageListener kafkaMessageListener;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private SongService songService;

    @Test
    void testListen() throws JsonProcessingException {

        UUID id = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        AuthorDto author = new AuthorDto(id2, "Name", Genre.CLASSICAL,
                new ImageDto(UUID.randomUUID(), "https://example.org/example"));

        when(songService.save(Mockito.any())).thenReturn(
                new SongDto(id, "Dr", author, new CoverDto("https://example.org/example"), "https://example.org/example"));

        Image photo = new Image();
        photo.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        photo.setId(UUID.randomUUID());
        photo.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        photo.setUrl("https://example.org/example");

        Author author2 = new Author();
        author2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        author2.setGenre(Genre.CLASSICAL);
        author2.setId(UUID.randomUUID());
        author2.setName("Name");
        author2.setPhoto(photo);
        author2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        UUID id3 = UUID.randomUUID();
        when(authorService.save(Mockito.any())).thenReturn(
                new AuthorDto(id3, "Name", Genre.CLASSICAL, new ImageDto(UUID.randomUUID(), "https://example.org/example")));
        when(authorService.findByName(Mockito.any())).thenReturn(author2);
        when(coverService.save(Mockito.any())).thenReturn(new CoverDto("https://example.org/example"));
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<SongMetaInfo>>any()))
                .thenReturn(new SongMetaInfo("Dr", "JaneDoe", "https://example.org/example", "https://example.org/example"));

        kafkaMessageListener.listen("Meta Info");

        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<SongMetaInfo>>any());
        verify(authorService).findByName(Mockito.any());
        verify(coverService).save(Mockito.any());
        verify(songService).save(Mockito.any());
    }

    @Test
    void testListen2() throws JsonProcessingException {

        UUID id = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        AuthorDto author = new AuthorDto(id2, "Name", Genre.CLASSICAL,
                new ImageDto(UUID.randomUUID(), "https://example.org/example"));

        when(songService.save(Mockito.any())).thenReturn(
                new SongDto(id, "Dr", author, new CoverDto("https://example.org/example"), "https://example.org/example"));

        Image photo = new Image();
        photo.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        photo.setId(UUID.randomUUID());
        photo.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        photo.setUrl("https://example.org/example");

        Author author2 = new Author();
        author2.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        author2.setGenre(Genre.CLASSICAL);
        author2.setId(UUID.randomUUID());
        author2.setName("Name");
        author2.setPhoto(photo);
        author2.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        UUID id3 = UUID.randomUUID();
        when(authorService.save(Mockito.any())).thenReturn(
                new AuthorDto(id3, "Name", Genre.CLASSICAL, new ImageDto(UUID.randomUUID(), "https://example.org/example")));
        when(authorService.findByName(Mockito.any())).thenReturn(author2);
        when(coverService.save(Mockito.any())).thenThrow(new IllegalArgumentException("Start saving {}"));
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<SongMetaInfo>>any()))
                .thenReturn(new SongMetaInfo("Dr", "JaneDoe", "https://example.org/example", "https://example.org/example"));

        assertThrows(IllegalArgumentException.class, () -> kafkaMessageListener.listen("Meta Info"));
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<SongMetaInfo>>any());
        verify(authorService).findByName(Mockito.any());
        verify(coverService).save(Mockito.any());
    }
}
