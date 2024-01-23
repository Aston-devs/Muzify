package ru.musify.musicservice.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.musify.musicservice.dto.AuthorDto;
import ru.musify.musicservice.dto.ImageDto;
import ru.musify.musicservice.entity.Author;
import ru.musify.musicservice.entity.Genre;
import ru.musify.musicservice.entity.Image;
import ru.musify.musicservice.repository.AuthorRepository;
import ru.musify.musicservice.util.mapper.AuthorMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AuthorServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AuthorServiceImplTest {

    private Author expAuthor;
    private AuthorDto expAuthorDto;

    @MockBean
    private AuthorMapper authorMapper;

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorServiceImpl authorServiceImpl;

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
    void testFindById() {

        Optional<Author> ofResult = Optional.of(expAuthor);

        when(authorRepository.findById(Mockito.any())).thenReturn(ofResult);
        when(authorMapper.toDto(Mockito.any())).thenReturn(expAuthorDto);

        AuthorDto actualFindByIdResult = authorServiceImpl.findById(UUID.randomUUID());

        verify(authorRepository).findById(Mockito.any());
        verify(authorMapper).toDto(Mockito.any());
        assertSame(expAuthorDto, actualFindByIdResult);
    }

    @Test
    void testFindAll() {

        when(authorRepository.findAll()).thenReturn(new ArrayList<>());

        List<AuthorDto> actualFindAllResult = authorServiceImpl.findAll();

        verify(authorRepository).findAll();
        assertTrue(actualFindAllResult.isEmpty());
    }

    @Test
    void testSave() {

        when(authorRepository.save(Mockito.any())).thenReturn(expAuthor);
        when(authorMapper.toDto(Mockito.any())).thenReturn(expAuthorDto);

        AuthorDto actualSaveResult = authorServiceImpl.save(expAuthor);

        verify(authorRepository).save(Mockito.any());
        verify(authorMapper).toDto(Mockito.any());
        assertSame(expAuthorDto, actualSaveResult);
    }

    @Test
    void testUpdate() {

        when(authorRepository.save(Mockito.any())).thenReturn(expAuthor);
        when(authorMapper.toDto(Mockito.any())).thenReturn(expAuthorDto);

        AuthorDto actualUpdateResult = authorServiceImpl.update(expAuthor);

        verify(authorRepository).save(Mockito.any());
        verify(authorMapper).toDto(Mockito.any());
        assertSame(expAuthorDto, actualUpdateResult);
    }

    @Test
    void testRemoveById() {

        doNothing().when(authorRepository).deleteById(Mockito.any());
        authorServiceImpl.removeById(UUID.randomUUID());
        verify(authorRepository).deleteById(Mockito.any());
    }

    @Test
    void testFindByName() {

        when(authorRepository.findAuthorByName(Mockito.any())).thenReturn(expAuthor);

        Author actualFindByNameResult = authorServiceImpl.findByName("JaneDoe");

        verify(authorRepository).findAuthorByName(Mockito.any());
        assertSame(expAuthor, actualFindByNameResult);
    }
}
