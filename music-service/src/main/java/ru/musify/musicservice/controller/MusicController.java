package ru.musify.musicservice.controller;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.musify.musicservice.handler.ResponseData;
import ru.musify.musicservice.handler.exception.UserNotExistException;
import ru.musify.musicservice.model.dto.SongDto;
import ru.musify.musicservice.model.dto.UserDto;
import ru.musify.musicservice.model.dto.UserSongsDto;
import ru.musify.musicservice.model.entity.User;
import ru.musify.musicservice.service.SongService;
import ru.musify.musicservice.service.UserService;
import ru.musify.musicservice.util.mapper.UserMapper;

@Slf4j
@RestController
@RequestMapping("/api/v1/musify/audios")
public class MusicController {

  private final SongService songService;

  private final UserService userService;

  private final UserMapper userMapper;

  @Autowired
  public MusicController(SongService songService, UserService userService, UserMapper userMapper) {
    this.songService = songService;
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  public List<SongDto> getAllSongs(
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "size", required = false, defaultValue = "20") int size) {
    return songService.findPaginatedSongs(page, size);
  }

  @GetMapping("/{userId}")
  @ResponseStatus(code = HttpStatus.OK)
  public UserSongsDto getUserSongs(@PathVariable UUID userId) {
    isUserExists(userId);

    List<SongDto> userSongs = userService.findSongsByUserId(userId);
    UserSongsDto userSongsDto = UserSongsDto.builder()
        .id(userId)
        .songs(userSongs)
        .build();

    log.info("User get own songs");

    return userSongsDto;
  }

  @PatchMapping("/add/{userId}")
  @ResponseStatus(code = HttpStatus.CREATED)
  public SongDto addSongToUser(@PathVariable UUID userId,
      @RequestParam(name = "trackId") UUID songId) {
    isUserExists(userId);

    SongDto songDto = songService.findById(songId);

    UserDto userDto = userService.findById(userId);
    userDto.userSongs().add(songDto);
    User user = userMapper.toEntity(userDto);

    userService.save(user);
    log.info("User {} add song with id {}", userId, songId);

    return songDto;
  }

  @DeleteMapping("/{userId}")
  @ResponseStatus(code = HttpStatus.OK)
  public ResponseData removeSongFromUser(@PathVariable UUID userId,
      @RequestParam(name = "trackId") UUID songId) {
    isUserExists(userId);

    SongDto songDto = songService.findById(songId);

    UserDto userDto = userService.findById(userId);
    boolean isRemoved = userDto.userSongs().removeIf(s -> s.equals(songDto));

    if (isRemoved) {
      User user = userMapper.toEntity(userDto);
      userService.save(user);
    }

    return ResponseData.builder()
        .statusCode(SC_OK)
        .message("Song is removed - " + isRemoved)
        .build();
  }

  public void isUserExists(UUID id) {
    if (!userService.isUserExists(id)) {
      log.debug("User does not exist with id {}", id);
      throw new UserNotExistException("User does not exist with id " + id);
    }
  }
}