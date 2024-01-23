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

/**
 * CoverServiceImpl class providing implementation for CoverService.
 */
@Slf4j
@Loggable
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoverServiceImpl implements CoverService {

    /**
     * The repository for accessing and managing Cover entities in the database.
     */
    private final CoverRepository repository;

    /**
     * The mapper for converting Cover entities to CoverDto objects and vice versa.
     */
    private final CoverMapper coverMapper;

    /**
     * Retrieves a cover by its ID.
     *
     * @param id The ID of the cover to be retrieved.
     * @return The DTO representation of the cover.
     * @throws EntityNotFoundException if the cover with the specified ID is not found.
     */
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

    /**
     * Retrieves all covers.
     *
     * @return A list of DTO representations for all covers.
     */
    @Override
    public List<CoverDto> findAll() {
        List<Cover> allCovers = repository.findAll();
        log.info("Found all covers");

        return allCovers.stream()
                .map(coverMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Saves a cover.
     *
     * @param cover The cover to be saved.
     * @return The DTO representation of the saved cover.
     */
    @Override
    @Transactional
    public CoverDto save(Cover cover) {
        Cover savedCover = repository.save(cover);
        log.info("Saved cover with id {}", savedCover.getId());

        return coverMapper.toDto(savedCover);
    }

    /**
     * Updates a cover.
     *
     * @param cover The cover to be updated.
     * @return The DTO representation of the updated cover.
     */
    @Override
    @Transactional
    public CoverDto update(Cover cover) {
        Cover updatedCover = repository.save(cover);
        log.info("Cover updated with id {}", updatedCover.getId());

        return coverMapper.toDto(updatedCover);
    }

    /**
     * Removes a cover by its ID.
     *
     * @param id The ID of the cover to be removed.
     */
    @Override
    @Transactional
    public void removeById(UUID id) {
        repository.deleteById(id);

        log.info("Removed cover with id {}", id);
    }
}