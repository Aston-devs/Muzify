package ru.musify.musicservice.service.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.musify.musicservice.dto.SongDto;
import ru.musify.musicservice.entity.Song;
import ru.musify.musicservice.handler.exception.EntityNotFoundException;
import ru.musify.musicservice.repository.SongRepository;
import ru.musify.musicservice.util.mapper.SongMapper;

@SpringBootTest
class SongServiceImplTest {

  @InjectMocks
  SongServiceImpl songService;

  @Mock
  SongRepository songRepository;

  @Mock
  SongMapper songMapper;

  Song song;

  SongDto expectedSongDto;

  UUID songId;

  @BeforeEach
  void setUp() {
    songId = UUID.randomUUID();

    song = Song.builder()
        .id(songId)
        .title("Title test")
        .url("12312412412.audio")
        .build();

    expectedSongDto = SongDto.builder()
        .id(songId)
        .title("Title test")
        .url("12312412412.audio")
        .build();

  }

  @Test
  void testFindById_ExistingSong_ReturnsSongDto() {
    when(songRepository.findById(songId)).thenReturn(Optional.ofNullable(song));
    when(songMapper.toDto(song)).thenReturn(expectedSongDto);

    SongDto result = songService.findById(songId);

    assertNotNull(result);
    assertEquals(expectedSongDto, result);
    verify(songRepository, times(1)).findById(songId);
    verify(songMapper, times(1)).toDto(song);
  }

  @Test
  void testFindById_NonExistingSong_ThrowsEntityNotFoundException() {
    when(songRepository.findById(songId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () ->
        songService.findById(songId)
    );

    verify(songRepository, times(1)).findById(songId);
    verify(songMapper, never()).toDto(any());
  }

  @Test
  void testSave_ValidSong_ReturnsSavedSongDto() {
    when(songRepository.save(song)).thenReturn(song);
    when(songMapper.toDto(song)).thenReturn(expectedSongDto);

    SongDto result = songService.save(song);

    assertNotNull(result);
    assertEquals(expectedSongDto, result);
    verify(songRepository, times(1)).save(song);
    verify(songMapper, times(1)).toDto(song);
  }

  @Test
  void testUpdate_ValidSong_ReturnsUpdatedSongDto() {
    when(songRepository.save(song)).thenReturn(song);
    when(songMapper.toDto(song)).thenReturn(expectedSongDto);

    SongDto result = songService.update(song);

    assertNotNull(result);
    assertEquals(expectedSongDto, result);
    verify(songRepository, times(1)).save(song);
    verify(songMapper, times(1)).toDto(song);
  }

  @Test
  void testRemoveById_ValidId_RemovesSongFromRepository() {
    songService.removeById(songId);

    verify(songRepository, times(1)).deleteById(songId);
  }
}