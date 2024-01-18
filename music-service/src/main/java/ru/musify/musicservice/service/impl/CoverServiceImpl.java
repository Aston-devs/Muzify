package ru.musify.musicservice.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musify.musicservice.aop.Loggable;
import ru.musify.musicservice.dto.CoverDto;
import ru.musify.musicservice.entity.Cover;
import ru.musify.musicservice.handler.exception.EntityNotFoundException;
import ru.musify.musicservice.repository.CoverRepository;
import ru.musify.musicservice.service.CoverService;
import ru.musify.musicservice.util.mapper.CoverMapper;

@Slf4j
@Loggable
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoverServiceImpl implements CoverService {

  private final CoverRepository repository;

  private final CoverMapper coverMapper;

  @Override
  public CoverDto findById(UUID id) {
    Cover cover = repository.findById(id)
        .orElseThrow(() -> {
          log.debug("Could not find cover with id {}", id);
          return new EntityNotFoundException("Cover not found with id " + id);
        });
    log.info("Cover found with id {}", id);

    return coverMapper.toDto(cover);
  }

  @Override
  public List<CoverDto> findAll() {
    List<Cover> allCovers = repository.findAll();
    log.info("Found all covers");

    return allCovers.stream()
        .map(coverMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public CoverDto save(Cover cover) {
    Cover savedCover = repository.save(cover);
    log.info("Saved cover with id {}", savedCover.getId());

    return coverMapper.toDto(savedCover);
  }

  @Override
  @Transactional
  public CoverDto update(Cover cover) {
    Cover updatedCover = repository.save(cover);
    log.info("Cover updated with id {}", updatedCover.getId());

    return coverMapper.toDto(updatedCover);
  }

  @Override
  @Transactional
  public void removeById(UUID id) {
    repository.deleteById(id);

    log.info("Removed cover with id {}", id);
  }
}
