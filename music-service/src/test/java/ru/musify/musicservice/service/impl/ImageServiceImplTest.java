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
    @MockBean
    private ImageMapper imageMapper;

    @MockBean
    private ImageRepository imageRepository;

    @Autowired
    private ImageServiceImpl imageServiceImpl;

    @Test
    void testFindById() {

        Image image = new Image();
        image.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        image.setId(UUID.randomUUID());
        image.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        image.setUrl("https://example.org/example");
        Optional<Image> ofResult = Optional.of(image);
        when(imageRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        ImageDto imageDto = new ImageDto(UUID.randomUUID(), "https://example.org/example");

        when(imageMapper.toDto(Mockito.<Image>any())).thenReturn(imageDto);

        ImageDto actualFindByIdResult = imageServiceImpl.findById(UUID.randomUUID());

        verify(imageRepository).findById(Mockito.<UUID>any());
        verify(imageMapper).toDto(Mockito.<Image>any());
        assertSame(imageDto, actualFindByIdResult);
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

        Image image = new Image();
        image.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        image.setId(UUID.randomUUID());
        image.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        image.setUrl("https://example.org/example");
        when(imageRepository.save(Mockito.<Image>any())).thenReturn(image);
        ImageDto imageDto = new ImageDto(UUID.randomUUID(), "https://example.org/example");

        when(imageMapper.toDto(Mockito.<Image>any())).thenReturn(imageDto);

        Image image2 = new Image();
        image2.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        image2.setId(UUID.randomUUID());
        image2.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        image2.setUrl("https://example.org/example");

        ImageDto actualSaveResult = imageServiceImpl.save(image2);

        verify(imageRepository).save(Mockito.<Image>any());
        verify(imageMapper).toDto(Mockito.<Image>any());
        assertSame(imageDto, actualSaveResult);
    }

    @Test
    void testUpdate() {

        Image image = new Image();
        image.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        image.setId(UUID.randomUUID());
        image.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        image.setUrl("https://example.org/example");
        when(imageRepository.save(Mockito.<Image>any())).thenReturn(image);
        ImageDto imageDto = new ImageDto(UUID.randomUUID(), "https://example.org/example");

        when(imageMapper.toDto(Mockito.<Image>any())).thenReturn(imageDto);

        Image image2 = new Image();
        image2.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        image2.setId(UUID.randomUUID());
        image2.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        image2.setUrl("https://example.org/example");

        ImageDto actualUpdateResult = imageServiceImpl.update(image2);

        verify(imageRepository).save(Mockito.<Image>any());
        verify(imageMapper).toDto(Mockito.<Image>any());
        assertSame(imageDto, actualUpdateResult);
    }

    @Test
    void testRemoveById() {

        doNothing().when(imageRepository).deleteById(Mockito.<UUID>any());

        imageServiceImpl.removeById(UUID.randomUUID());

        verify(imageRepository).deleteById(Mockito.<UUID>any());
    }
}