package ru.musify.musicservice.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.musify.musicservice.dto.CoverDto;
import ru.musify.musicservice.entity.Cover;
import ru.musify.musicservice.repository.CoverRepository;
import ru.musify.musicservice.util.mapper.CoverMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CoverServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CoverServiceImplTest {

    private Cover expCover;
    private CoverDto expCoverDto;

    @MockBean
    private CoverMapper coverMapper;

    @MockBean
    private CoverRepository coverRepository;

    @Autowired
    private CoverServiceImpl coverServiceImpl;

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
    void testFindById() {

        Optional<Cover> ofResult = Optional.of(expCover);

        when(coverRepository.findById(Mockito.any())).thenReturn(ofResult);
        when(coverMapper.toDto(Mockito.any())).thenReturn(expCoverDto);

        CoverDto actualFindByIdResult = coverServiceImpl.findById(UUID.randomUUID());

        verify(coverRepository).findById(Mockito.any());
        verify(coverMapper).toDto(Mockito.any());
        assertSame(expCoverDto, actualFindByIdResult);
    }

    @Test
    void testFindAll() {

        when(coverRepository.findAll()).thenReturn(new ArrayList<>());

        List<CoverDto> actualFindAllResult = coverServiceImpl.findAll();

        verify(coverRepository).findAll();
        assertTrue(actualFindAllResult.isEmpty());
    }

    @Test
    void testSave() {

        when(coverRepository.save(Mockito.any())).thenReturn(expCover);
        when(coverMapper.toDto(Mockito.any())).thenReturn(expCoverDto);

        CoverDto actualSaveResult = coverServiceImpl.save(expCover);

        verify(coverRepository).save(Mockito.any());
        verify(coverMapper).toDto(Mockito.any());
        assertSame(expCoverDto, actualSaveResult);
    }

    @Test
    void testRemoveById() {

        doNothing().when(coverRepository).deleteById(Mockito.any());

        coverServiceImpl.removeById(UUID.randomUUID());

        verify(coverRepository).deleteById(Mockito.any());
    }

    @Test
    void testUpdate() {

        when(coverRepository.save(Mockito.any())).thenReturn(expCover);
        when(coverMapper.toDto(Mockito.any())).thenReturn(expCoverDto);

        CoverDto actualUpdateResult = coverServiceImpl.update(expCover);

        verify(coverRepository).save(Mockito.any());
        verify(coverMapper).toDto(Mockito.any());
        assertSame(expCoverDto, actualUpdateResult);
    }
}
