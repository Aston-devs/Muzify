package ru.musify.musicservice.listener;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.UUID;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.musify.musicservice.dto.AuthorDto;
import ru.musify.musicservice.dto.CoverDto;
import ru.musify.musicservice.dto.ImageDto;
import ru.musify.musicservice.dto.SongDto;
import ru.musify.musicservice.dto.SongMetaInfo;
import ru.musify.musicservice.dto.UserMetainfo;
import ru.musify.musicservice.entity.Author;
import ru.musify.musicservice.entity.Cover;
import ru.musify.musicservice.entity.Genre;
import ru.musify.musicservice.entity.Image;
import ru.musify.musicservice.service.AuthorService;
import ru.musify.musicservice.service.CoverService;
import ru.musify.musicservice.service.SongService;
import ru.musify.musicservice.service.UserService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.musify.musicservice.dto.SongDto;
import ru.musify.musicservice.dto.ImageDto;
import ru.musify.musicservice.dto.CoverDto;
import ru.musify.musicservice.dto.AuthorDto;
import ru.musify.musicservice.dto.SongMetaInfo;
import ru.musify.musicservice.service.SongService;
import ru.musify.musicservice.service.CoverService;
import ru.musify.musicservice.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ContextConfiguration(classes = {KafkaMessageListener.class})
@ExtendWith(SpringExtension.class)
class KafkaMessageListenerTest {

    private Author expAuthor;
    private AuthorDto expAuthorDto;
    private Cover expCover;
    private CoverDto expCoverDto;
    private SongDto expSongDto;
    private ImageDto expImageDto;

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

    @MockBean
    private UserService userService;

    @BeforeEach
    void setup() {

        Image expImage = new Image();
        expImage.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        expImage.setId(UUID.randomUUID());
        expImage.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        expImage.setUrl("https://example.org/example");

        expAuthor = Author.builder()
                .id(UUID.randomUUID())
                .name("AuthorName")
                .genre(Genre.CLASSICAL)
                .photo(expImage)
                .build();

        expAuthorDto = AuthorDto.builder()
                .id(expAuthor.getId())
                .name(expAuthor.getName())
                .genre(Genre.CLASSICAL)
                .photo(
                        ImageDto.builder()
                                .id(expImage.getId())
                                .url(expImage.getUrl())
                                .build()
                )
                .build();

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

        expCover = new Cover();
        expCover.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        expCover.setId(UUID.randomUUID());
        expCover.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        expCover.setUrl("https://example.org/example");

        expCoverDto = CoverDto.builder()
                .url("https://example.org/example")
                .build();

    }

    @Test
    @DisplayName("Test Listen - successful message processing")
    void testListen() throws JsonProcessingException {

        when(songService.save(Mockito.any())).thenReturn(expSongDto);
        when(coverService.save(Mockito.any())).thenReturn(expCoverDto);
        when(authorService.save(Mockito.any())).thenReturn(expAuthorDto);
        when(authorService.findByName(Mockito.any())).thenReturn(expAuthor);
        when(authorService.save(Mockito.any())).thenReturn(expAuthorDto);
        when(authorService.findByName(Mockito.any())).thenReturn(expAuthor);
        when(coverService.save(Mockito.any())).thenReturn(expCoverDto);
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<SongMetaInfo>>any())).thenReturn(
                new SongMetaInfo("Dr", "JaneDoe", "https://example.org/example", "https://example.org/example"));

        kafkaMessageListener.listen("Meta Info");

        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<SongMetaInfo>>any());
        verify(authorService).findByName(Mockito.any());
        verify(coverService).save(Mockito.any());
        verify(songService).save(Mockito.any());
    }

    @Test
    @DisplayName("Test Listen - exception during message processing")
    void testListen2() throws JsonProcessingException {

        when(authorService.findByName(Mockito.any())).thenThrow(new IllegalArgumentException("Start saving {}"));
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<SongMetaInfo>>any()))
                .thenReturn(new SongMetaInfo("Dr", "JaneDoe", "https://example.org/example", "https://example.org/example"));

        assertThrows(IllegalArgumentException.class, () -> kafkaMessageListener.listen("Meta Info"));
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<SongMetaInfo>>any());
        verify(authorService).findByName(Mockito.any());
    }

    @Test
    @DisplayName("Test Listen User Topic - exception during processing")
    void testListenUserTopic() throws JsonProcessingException {

        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<UserMetainfo>>any()))
                .thenThrow(new IllegalArgumentException("Start saving {}"));

        assertThrows(IllegalArgumentException.class, () -> kafkaMessageListener.listenUserTopic("User Metainfo"));
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<UserMetainfo>>any());
        when(songService.save(Mockito.any())).thenReturn(expSongDto);
        when(authorService.save(Mockito.any())).thenReturn(expAuthorDto);
        when(authorService.findByName(Mockito.any())).thenReturn(expAuthor);
        when(coverService.save(Mockito.any())).thenThrow(new IllegalArgumentException("Start saving {}"));
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<SongMetaInfo>>any())).thenReturn(
                new SongMetaInfo("Dr", "JaneDoe", "https://example.org/example", "https://example.org/example"));
        assertThrows(IllegalArgumentException.class, () -> kafkaMessageListener.listen("Meta Info"));
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<SongMetaInfo>>any());
        verify(authorService).findByName(Mockito.any());
        verify(coverService).save(Mockito.any());
    }
}
