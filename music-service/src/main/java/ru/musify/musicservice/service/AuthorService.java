package ru.musify.musicservice.service;

import ru.musify.musicservice.dto.AuthorDto;
import ru.musify.musicservice.entity.Author;

/**
 * AuthorService interface for providing service methods related to Author entities.
 */
public interface AuthorService extends BaseService<Author, AuthorDto> {

    /**
     * Finds an author by their name.
     *
     * @param author The name of the author to be found.
     * @return The author with the specified name.
     */
    Author findByName(String author);
}