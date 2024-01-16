package ru.musify.musicservice.service.impl;

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
    @MockBean
    private AuthorMapper authorMapper;

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorServiceImpl authorServiceImpl;

    @Test
    void testFindById() {

        Image photo = new Image();
        photo.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        photo.setId(UUID.randomUUID());
        photo.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        photo.setUrl("https://example.org/example");

        Author author = new Author();
        author.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        author.setGenre(Genre.CLASSICAL);
        author.setId(UUID.randomUUID());
        author.setName("Name");
        author.setPhoto(photo);
        author.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        Optional<Author> ofResult = Optional.of(author);
        when(authorRepository.findById(Mockito.any())).thenReturn(ofResult);
        UUID id = UUID.randomUUID();
        AuthorDto authorDto = new AuthorDto(id, "Name", Genre.CLASSICAL,
                new ImageDto(UUID.randomUUID(), "https://example.org/example"));

        when(authorMapper.toDto(Mockito.any())).thenReturn(authorDto);

        AuthorDto actualFindByIdResult = authorServiceImpl.findById(UUID.randomUUID());

        verify(authorRepository).findById(Mockito.any());
        verify(authorMapper).toDto(Mockito.any());
        assertSame(authorDto, actualFindByIdResult);
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

        Image photo = new Image();
        photo.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        photo.setId(UUID.randomUUID());
        photo.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        photo.setUrl("https://example.org/example");

        Author author = new Author();
        author.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        author.setGenre(Genre.CLASSICAL);
        author.setId(UUID.randomUUID());
        author.setName("Name");
        author.setPhoto(photo);
        author.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        when(authorRepository.save(Mockito.any())).thenReturn(author);
        UUID id = UUID.randomUUID();
        AuthorDto authorDto = new AuthorDto(id, "Name", Genre.CLASSICAL,
                new ImageDto(UUID.randomUUID(), "https://example.org/example"));

        when(authorMapper.toDto(Mockito.any())).thenReturn(authorDto);

        Image photo2 = new Image();
        photo2.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        photo2.setId(UUID.randomUUID());
        photo2.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        photo2.setUrl("https://example.org/example");

        Author author2 = new Author();
        author2.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        author2.setGenre(Genre.CLASSICAL);
        author2.setId(UUID.randomUUID());
        author2.setName("Name");
        author2.setPhoto(photo2);
        author2.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());

        AuthorDto actualSaveResult = authorServiceImpl.save(author2);

        verify(authorRepository).save(Mockito.any());
        verify(authorMapper).toDto(Mockito.any());
        assertSame(authorDto, actualSaveResult);
    }

    @Test
    void testUpdate() {

        Image photo = new Image();
        photo.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        photo.setId(UUID.randomUUID());
        photo.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        photo.setUrl("https://example.org/example");

        Author author = new Author();
        author.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        author.setGenre(Genre.CLASSICAL);
        author.setId(UUID.randomUUID());
        author.setName("Name");
        author.setPhoto(photo);
        author.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        when(authorRepository.save(Mockito.any())).thenReturn(author);
        UUID id = UUID.randomUUID();
        AuthorDto authorDto = new AuthorDto(id, "Name", Genre.CLASSICAL,
                new ImageDto(UUID.randomUUID(), "https://example.org/example"));

        when(authorMapper.toDto(Mockito.any())).thenReturn(authorDto);

        Image photo2 = new Image();
        photo2.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        photo2.setId(UUID.randomUUID());
        photo2.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        photo2.setUrl("https://example.org/example");

        Author author2 = new Author();
        author2.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        author2.setGenre(Genre.CLASSICAL);
        author2.setId(UUID.randomUUID());
        author2.setName("Name");
        author2.setPhoto(photo2);
        author2.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());

        AuthorDto actualUpdateResult = authorServiceImpl.update(author2);

        verify(authorRepository).save(Mockito.any());
        verify(authorMapper).toDto(Mockito.any());
        assertSame(authorDto, actualUpdateResult);
    }

    @Test
    void testRemoveById() {

        doNothing().when(authorRepository).deleteById(Mockito.any());

        authorServiceImpl.removeById(UUID.randomUUID());

        verify(authorRepository).deleteById(Mockito.any());
    }

    @Test
    void testFindByName() {

        Image photo = new Image();
        photo.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        photo.setId(UUID.randomUUID());
        photo.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        photo.setUrl("https://example.org/example");

        Author author = new Author();
        author.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        author.setGenre(Genre.CLASSICAL);
        author.setId(UUID.randomUUID());
        author.setName("Name");
        author.setPhoto(photo);
        author.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        when(authorRepository.findAuthorByName(Mockito.any())).thenReturn(author);

        Author actualFindByNameResult = authorServiceImpl.findByName("JaneDoe");

        verify(authorRepository).findAuthorByName(Mockito.any());
        assertSame(author, actualFindByNameResult);
    }
}
