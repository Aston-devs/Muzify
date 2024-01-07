package ru.musify.musicservice.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musify.musicservice.handler.exception.EntityNotFoundException;
import ru.musify.musicservice.model.dto.ImageDto;
import ru.musify.musicservice.model.entity.Image;
import ru.musify.musicservice.repository.ImageRepository;
import ru.musify.musicservice.service.ImageService;
import ru.musify.musicservice.util.mapper.ImageMapper;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

  private final ImageRepository repository;

  private final ImageMapper imageMapper;

  @Autowired
  public ImageServiceImpl(ImageRepository repository, ImageMapper imageMapper) {
    this.repository = repository;
    this.imageMapper = imageMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public ImageDto findById(UUID id) {
    Image image = repository.findById(id)
        .orElseThrow(() -> {
          log.debug("Could not find image with id {}", id);
          return new EntityNotFoundException("Image not found with id " + id);
        });
    log.info("Image found with id {}", id);

    return imageMapper.toDto(image);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ImageDto> findAll() {
    List<Image> allImages = repository.findAll();
    log.info("Found all images");

    return allImages.stream()
        .map(imageMapper::toDto)
        .toList();
  }

  @Override
  @Transactional
  public ImageDto save(Image image) {
    Image savedImage = repository.save(image);
    log.info("Saved image with id {}", savedImage.getId());

    return imageMapper.toDto(savedImage);
  }

  @Override
  @Transactional
  public ImageDto update(Image image) {
    Image updatedImage = repository.save(image);
    log.info("Image updated with id {}", updatedImage.getId());

    return imageMapper.toDto(updatedImage);
  }

  @Override
  @Transactional
  public void removeById(UUID id) {
    repository.deleteById(id);

    log.info("Removed image with id {}", id);
  }
}
