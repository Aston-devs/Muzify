package ru.musify.musicservice.controller;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.musify.musicservice.dto.SongDto;
import ru.musify.musicservice.dto.UserDto;
import ru.musify.musicservice.service.SongService;
import ru.musify.musicservice.service.UserService;
import ru.musify.musicservice.util.mapper.UserMapper;


@WebMvcTest(MusicController.class)
@ExtendWith(SpringExtension.class)
class MusicControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  SongService songService;

  @MockBean
  UserService userService;

  @MockBean
  UserMapper userMapper;

  List<SongDto> songsDto;

  UUID userId;

  UUID songId;

  SongDto songDto;

  UserDto userDto;

  String baseUrl = "/api/v1/musify/audios";


  @BeforeEach
  void setUp() {
    userId = UUID.randomUUID();
    songId = UUID.randomUUID();

    songDto = SongDto.builder()
        .id(songId)
        .title("Test title")
        .url("53532535.audio")
        .build();

    userDto = UserDto.builder()
        .id(userId)
        .userSongs(new ArrayList<>())
        .build();

    songsDto = List.of(
        SongDto.builder()
            .id(randomUUID())
            .title("Test title")
            .url("https://test-1.com")
            .build(),
        SongDto.builder()
            .id(randomUUID())
            .title("Test title 2")
            .url("https://test-2.com")
            .build()
    );
  }

  @Test
  @DisplayName("Test get user songs must return correct list of songs of user and status OK")
  void getUsersSongsTest() throws Exception {
    List<SongDto> mockUserSongs = new ArrayList<>(songsDto);
    when(userService.isUserExists(userId)).thenReturn(true);
    when(userService.findSongsByUserId(userId)).thenReturn(mockUserSongs);

    mockMvc.perform(get(baseUrl + "/{userId}", userId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.songs", hasSize(songsDto.size())))
        .andExpect(jsonPath("$.songs[*].id",
            containsInAnyOrder(songsDto.stream().map(song -> song.id().toString()).toArray())))
        .andExpect(jsonPath("$.songs[*].title",
            containsInAnyOrder(songsDto.stream().map(SongDto::title).toArray())))
        .andExpect(jsonPath("$.songs[*].url",
            containsInAnyOrder(songsDto.stream().map(SongDto::url).toArray())));
  }

  @Test
  @DisplayName("Test add song to user must return status isCreated and content type JSON")
  void addSongToUserTest() throws Exception {
    when(userService.isUserExists(userId)).thenReturn(true);
    when(songService.findById(songId)).thenReturn(songDto);
    when(userService.findById(userId)).thenReturn(userDto);

    mockMvc.perform(patch(baseUrl + "/{userId}", userId)
            .param("trackId", songId.toString()))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    assertEquals(1, userDto.userSongs().size());
    assertEquals(songDto, userDto.userSongs().get(0));
  }

  @Test
  @DisplayName("Test remove song from user must return status OK and message that song is removed")
  void removeSongFromUserTest() throws Exception {
    userDto.userSongs().add(songDto);
    when(userService.isUserExists(userId)).thenReturn(true);
    when(songService.findById(songId)).thenReturn(songDto);
    when(userService.findById(userId)).thenReturn(userDto);

    mockMvc.perform(delete(baseUrl + "/{userId}", userId)
            .param("trackId", songId.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode", is(SC_OK)))
        .andExpect(jsonPath("$.message", is("Song is removed - true")));

    assertEquals(0, userDto.userSongs().size());
  }
}