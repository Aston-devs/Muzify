package ru.musify.musicservice.util.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;;
import ru.musify.musicservice.dto.CoverDto;
import ru.musify.musicservice.entity.Cover;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoverMapperTest {
    private Cover expCover;
    private CoverDto expCoverDto;

    @Mock
    private  CoverMapper coverMapper;

    @BeforeEach
    void setUp() {

        expCover = Cover.builder()
                .id(UUID.randomUUID())
                .url("https://example.org/cover")
                .build();

        expCoverDto = CoverDto.builder()
                .url("https://example.org/cover")
                .build();
    }

    @Test
    void testToEntity() {

        when(coverMapper.toEntity(expCoverDto)).thenReturn(expCover);
        Cover actual = coverMapper.toEntity(expCoverDto);
        assertEquals(expCover, actual);

    }

    @Test
    void testToDto() {

        when(coverMapper.toDto(expCover)).thenReturn(expCoverDto);
        CoverDto actual = coverMapper.toDto(expCover);
        assertEquals(expCoverDto, actual);

    }
}
