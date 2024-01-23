package ru.musify.musicservice.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musify.musicservice.aop.Loggable;
import ru.musify.musicservice.dto.ImageDto;
import ru.musify.musicservice.entity.Image;
import ru.musify.musicservice.handler.exception.EntityNotFoundException;
import ru.musify.musicservice.repository.ImageRepository;
import ru.musify.musicservice.service.ImageService;
import ru.musify.musicservice.util.mapper.ImageMapper;

/**
 * ImageServiceImpl class providing implementation for ImageService.
 */
@Slf4j
@Loggable
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {

    /**
     * The repository for accessing and managing Image entities in the database.
     */
    private final ImageRepository repository;

    /**
     * The mapper for converting Image entities to ImageDto objects and vice versa.
     */
    private final ImageMapper imageMapper;

    /**
     * Retrieves an image by its ID.
     *
     * @param id The ID of the image to be retrieved.
     * @return The DTO representation of the image.
     * @throws EntityNotFoundException if the image with the specified ID is not found.
     */
    @Override
    public ImageDto findById(UUID id) {
        Image image = repository.findById(id)
                .orElseThrow(() -> {
                    log.debug("Could not find image with id {}", id);
                    return new EntityNotFoundException("Image not found with id " + id);
                });
        log.info("Image found with id {}", id);

        return imageMapper.toDto(image);
    }

    /**
     * Retrieves all images.
     *
     * @return A list of DTO representations for all images.
     */
    @Override
    public List<ImageDto> findAll() {
        List<Image> allImages = repository.findAll();
        log.info("Found all images");

        return allImages.stream()
                .map(imageMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Saves an image.
     *
     * @param image The image to be saved.
     * @return The DTO representation of the saved image.
     */
    @Override
    @Transactional
    public ImageDto save(Image image) {
        Image savedImage = repository.save(image);
        log.info("Saved image with id {}", savedImage.getId());

        return imageMapper.toDto(savedImage);
    }

    /**
     * Updates an image.
     *
     * @param image The image to be updated.
     * @return The DTO representation of the updated image.
     */
    @Override
    @Transactional
    public ImageDto update(Image image) {
        Image updatedImage = repository.save(image);
        log.info("Image updated with id {}", updatedImage.getId());

        return imageMapper.toDto(updatedImage);
    }

    /**
     * Removes an image by its ID.
     *
     * @param id The ID of the image to be removed.
     */
    @Override
    @Transactional
    public void removeById(UUID id) {
        repository.deleteById(id);

        log.info("Removed image with id {}", id);
    }
}