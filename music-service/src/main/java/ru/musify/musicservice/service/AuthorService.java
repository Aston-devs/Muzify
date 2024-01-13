package ru.musify.musicservice.service;

import ru.musify.musicservice.dto.AuthorDto;
import ru.musify.musicservice.entity.Author;

public interface AuthorService extends BaseService<Author, AuthorDto> {

  Author findByName(String author);
}
