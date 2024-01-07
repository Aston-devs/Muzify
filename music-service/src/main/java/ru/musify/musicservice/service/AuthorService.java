package ru.musify.musicservice.service;

import ru.musify.musicservice.model.dto.AuthorDto;
import ru.musify.musicservice.model.entity.Author;

public interface AuthorService extends BaseService<Author, AuthorDto> {
  Author findByName(String author);
}
