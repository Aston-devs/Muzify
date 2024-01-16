package ru.musify.musicservice.service.impl;

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

import java.time.LocalDate;
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
    @MockBean
    private CoverMapper coverMapper;

    @MockBean
    private CoverRepository coverRepository;

    @Autowired
    private CoverServiceImpl coverServiceImpl;

    @Test
    void testFindById() {

        Cover cover = new Cover();
        cover.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        cover.setId(UUID.randomUUID());
        cover.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        cover.setUrl("https://example.org/example");
        Optional<Cover> ofResult = Optional.of(cover);
        when(coverRepository.findById(Mockito.any())).thenReturn(ofResult);
        CoverDto coverDto = new CoverDto("https://example.org/example");
        when(coverMapper.toDto(Mockito.any())).thenReturn(coverDto);

        CoverDto actualFindByIdResult = coverServiceImpl.findById(UUID.randomUUID());

        verify(coverRepository).findById(Mockito.any());
        verify(coverMapper).toDto(Mockito.any());
        assertSame(coverDto, actualFindByIdResult);
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

        Cover cover = new Cover();
        cover.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        cover.setId(UUID.randomUUID());
        cover.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        cover.setUrl("https://example.org/example");
        when(coverRepository.save(Mockito.any())).thenReturn(cover);
        CoverDto coverDto = new CoverDto("https://example.org/example");
        when(coverMapper.toDto(Mockito.any())).thenReturn(coverDto);

        Cover cover2 = new Cover();
        cover2.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        cover2.setId(UUID.randomUUID());
        cover2.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        cover2.setUrl("https://example.org/example");

        CoverDto actualSaveResult = coverServiceImpl.save(cover2);

        verify(coverRepository).save(Mockito.any());
        verify(coverMapper).toDto(Mockito.any());
        assertSame(coverDto, actualSaveResult);
    }

    @Test
    void testUpdate() {

        Cover cover = new Cover();
        cover.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        cover.setId(UUID.randomUUID());
        cover.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        cover.setUrl("https://example.org/example");
        when(coverRepository.save(Mockito.any())).thenReturn(cover);
        CoverDto coverDto = new CoverDto("https://example.org/example");
        when(coverMapper.toDto(Mockito.any())).thenReturn(coverDto);

        Cover cover2 = new Cover();
        cover2.setCreatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        cover2.setId(UUID.randomUUID());
        cover2.setUpdatedAt(LocalDate.of(2024, 1, 1).atStartOfDay());
        cover2.setUrl("https://example.org/example");

        CoverDto actualUpdateResult = coverServiceImpl.update(cover2);

        verify(coverRepository).save(Mockito.any());
        verify(coverMapper).toDto(Mockito.any());
        assertSame(coverDto, actualUpdateResult);
    }

    @Test
    void testRemoveById() {

        doNothing().when(coverRepository).deleteById(Mockito.any());

        coverServiceImpl.removeById(UUID.randomUUID());

        verify(coverRepository).deleteById(Mockito.any());
    }
}
