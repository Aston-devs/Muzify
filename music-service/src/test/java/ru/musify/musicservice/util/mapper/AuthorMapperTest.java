package ru.musify.musicservice.util.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.musify.musicservice.dto.AuthorDto;
import ru.musify.musicservice.dto.ImageDto;
import ru.musify.musicservice.entity.Author;
import ru.musify.musicservice.entity.Genre;
import ru.musify.musicservice.entity.Image;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorMapperTest {
    private Author expAuthor;
    private AuthorDto expAuthorDto;

    @Mock
    private  AuthorMapper authorMapper;

    @BeforeEach
    void setUp() {
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
    }

    @Test
    void testToEntity() {

        when(authorMapper.toEntity(expAuthorDto)).thenReturn(expAuthor);
        Author actual = authorMapper.toEntity(expAuthorDto);
        assertEquals(expAuthor, actual);

    }

    @Test
    void testToDto() {

        when(authorMapper.toDto(expAuthor)).thenReturn(expAuthorDto);
        AuthorDto actual = authorMapper.toDto(expAuthor);
        assertEquals(expAuthorDto, actual);

    }
}
