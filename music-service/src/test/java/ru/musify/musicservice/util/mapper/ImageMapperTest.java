package ru.musify.musicservice.util.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;;
import ru.musify.musicservice.dto.CoverDto;
import ru.musify.musicservice.dto.ImageDto;
import ru.musify.musicservice.entity.Cover;
import ru.musify.musicservice.entity.Image;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageMapperTest {
    private Image expImage;
    private ImageDto expImageDto;

    @Mock
    private  ImageMapper imageMapper;

    @BeforeEach
    void setUp() {

        expImage = new Image();
        expImage.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        expImage.setId(UUID.randomUUID());
        expImage.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        expImage.setUrl("https://example.org/example");

    }

    @Test
    void testToEntity() {

        when(imageMapper.toEntity(expImageDto)).thenReturn(expImage);
        Image actual = imageMapper.toEntity(expImageDto);
        assertEquals(expImage, actual);

    }

    @Test
    void testToDto() {

        when(imageMapper.toDto(expImage)).thenReturn(expImageDto);
        ImageDto actual = imageMapper.toDto(expImage);
        assertEquals(expImageDto, actual);

    }
}
