package ru.musify.musicservice.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musify.musicservice.aop.Loggable;
import ru.musify.musicservice.dto.AuthorDto;
import ru.musify.musicservice.entity.Author;
import ru.musify.musicservice.handler.exception.EntityNotFoundException;
import ru.musify.musicservice.repository.AuthorRepository;
import ru.musify.musicservice.service.AuthorService;
import ru.musify.musicservice.util.mapper.AuthorMapper;

/**
 * AuthorServiceImpl class providing implementation for AuthorService.
 */
@Slf4j
@Loggable
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    private final AuthorMapper authorMapper;

    /**
     * Retrieves an author by their ID.
     *
     * @param id The ID of the author to be retrieved.
     * @return The DTO representation of the author.
     * @throws EntityNotFoundException if the author with the specified ID is not found.
     */
    @Override
    public AuthorDto findById(UUID id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> {
                    log.debug("Could not find author with id {}", id);
                    return new EntityNotFoundException("Author not found with id " + id);
                });
        log.info("Author found with id {}", id);

        return authorMapper.toDto(author);
    }

    /**
     * Retrieves all authors.
     *
     * @return A list of DTO representations for all authors.
     */
    @Override
    public List<AuthorDto> findAll() {
        List<Author> allAuthors = repository.findAll();
        log.info("Found all authors");

        return allAuthors.stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Saves an author.
     *
     * @param author The author to be saved.
     * @return The DTO representation of the saved author.
     */
    @Override
    @Transactional
    public AuthorDto save(Author author) {
        Author savedAuthor = repository.save(author);
        log.info("Author saved with id {}", savedAuthor.getId());

        return authorMapper.toDto(savedAuthor);
    }

    /**
     * Updates an author.
     *
     * @param author The author to be updated.
     * @return The DTO representation of the updated author.
     */
    @Override
    @Transactional
    public AuthorDto update(Author author) {
        Author updatedAuthor = repository.save(author);
        log.info("Author updated with id {}", updatedAuthor.getId());

        return authorMapper.toDto(updatedAuthor);
    }

    /**
     * Removes an author by their ID.
     *
     * @param id The ID of the author to be removed.
     */
    @Override
    @Transactional
    public void removeById(UUID id) {
        repository.deleteById(id);

        log.info("Removed author with id {}", id);
    }

    /**
     * Finds an author by their name.
     *
     * @param authorName The name of the author to be found.
     * @return The author with the specified name, or null if not found.
     */
    @Override
    public Author findByName(String authorName) {
        Author author = repository.findAuthorByName(authorName);
        if (author == null) {
            log.debug("Couldn't find author with name {}", authorName);
        }
        return author;
    }
}