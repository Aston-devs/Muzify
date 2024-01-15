package ru.musify.musicservice.service.impl;

import java.util.List;
import java.util.UUID;
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

@Slf4j
@Loggable
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository repository;

  private final AuthorMapper authorMapper;

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

  @Override
  public List<AuthorDto> findAll() {
    List<Author> allAuthors = repository.findAll();
    log.info("Found all authors");

    return allAuthors.stream()
        .map(authorMapper::toDto)
        .toList();
  }

  @Override
  @Transactional
  public AuthorDto save(Author author) {
    Author savedAuthor = repository.save(author);
    log.info("Author saved with id {}", savedAuthor.getId());

    return authorMapper.toDto(savedAuthor);
  }

  @Override
  @Transactional
  public AuthorDto update(Author author) {
    Author updatedAuthor = repository.save(author);
    log.info("Author updated with id {}", updatedAuthor.getId());

    return authorMapper.toDto(updatedAuthor);
  }

  @Override
  @Transactional
  public void removeById(UUID id) {
    repository.deleteById(id);

    log.info("Removed author with id {}", id);
  }

  @Override
  public Author findByName(String authorName) {
    Author author = repository.findAuthorByName(authorName);
    if (author == null) {
      log.debug("Couldn't find author with name {}", authorName);
    }
    return author;
  }
}
