package ru.musify.musicservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.musify.musicservice.dto.ImageDto;
import ru.musify.musicservice.entity.Image;
import ru.musify.musicservice.repository.ImageRepository;
import ru.musify.musicservice.util.mapper.ImageMapper;

@ContextConfiguration(classes = {ImageServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ImageServiceImplTest {

    private Image expImage;
    private ImageDto expImageDto;

    @MockBean
    private ImageMapper imageMapper;

    @MockBean
    private ImageRepository imageRepository;

    @Autowired
    private ImageServiceImpl imageServiceImpl;

    @BeforeEach
    void setUp() {

        expImage = new Image();
        expImage.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        expImage.setId(UUID.randomUUID());
        expImage.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        expImage.setUrl("https://example.org/example");

        expImageDto = new ImageDto(UUID.randomUUID(), "https://example.org/example");
    }


    @Test
    void testFindById() {

        Optional<Image> ofResult = Optional.of(expImage);

        when(imageRepository.findById(Mockito.any())).thenReturn(ofResult);
        when(imageMapper.toDto(Mockito.any())).thenReturn(expImageDto);

        ImageDto actualFindByIdResult = imageServiceImpl.findById(UUID.randomUUID());

        verify(imageRepository).findById(Mockito.any());
        verify(imageMapper).toDto(Mockito.any());
        assertSame(expImageDto, actualFindByIdResult);
    }

    @Test
    void testFindAll() {

        when(imageRepository.findAll()).thenReturn(new ArrayList<>());

        List<ImageDto> actualFindAllResult = imageServiceImpl.findAll();

        verify(imageRepository).findAll();
        assertTrue(actualFindAllResult.isEmpty());
    }

    @Test
    void testSave() {

        when(imageRepository.save(Mockito.any())).thenReturn(expImage);
        when(imageMapper.toDto(Mockito.any())).thenReturn(expImageDto);

        ImageDto actualSaveResult = imageServiceImpl.save(expImage);

        verify(imageRepository).save(Mockito.any());
        verify(imageMapper).toDto(Mockito.any());
        assertSame(expImageDto, actualSaveResult);
    }

    @Test
    void testUpdate() {

        when(imageRepository.save(Mockito.any())).thenReturn(expImage);
        when(imageMapper.toDto(Mockito.any())).thenReturn(expImageDto);

        ImageDto actualUpdateResult = imageServiceImpl.update(expImage);

        verify(imageRepository).save(Mockito.any());
        verify(imageMapper).toDto(Mockito.any());
        assertSame(expImageDto, actualUpdateResult);
    }

    @Test
    void testRemoveById() {

        doNothing().when(imageRepository).deleteById(Mockito.any());

        imageServiceImpl.removeById(UUID.randomUUID());

        verify(imageRepository).deleteById(Mockito.any());
    }
}