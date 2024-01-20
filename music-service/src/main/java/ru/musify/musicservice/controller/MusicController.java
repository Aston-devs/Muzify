package ru.musify.musicservice.controller;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.musify.musicservice.dto.SongDto;
import ru.musify.musicservice.dto.UserDto;
import ru.musify.musicservice.dto.UserSongsDto;
import ru.musify.musicservice.entity.User;
import ru.musify.musicservice.handler.ResponseData;
import ru.musify.musicservice.handler.exception.SongAlreadyAddedException;
import ru.musify.musicservice.handler.exception.UserNotExistException;
import ru.musify.musicservice.service.SongService;
import ru.musify.musicservice.service.UserService;
import ru.musify.musicservice.util.mapper.UserMapper;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/musify/audios")
public class MusicController {

    private final SongService songService;

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SongDto> getAllSongs(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size) {
        return songService.findPaginatedSongs(page, size);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserSongsDto getUserSongs(@PathVariable UUID userId) {
        isUserExists(userId);

        List<SongDto> userSongs = userService.findSongsByUserId(userId);
        UserSongsDto userSongsDto = UserSongsDto.builder()
                .songs(userSongs)
                .build();

        log.info("User get own songs");

        return userSongsDto;
    }

    @PatchMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SongDto addSongToUser(@PathVariable UUID userId,
                                 @RequestParam(name = "trackId") UUID songId) {
        isUserExists(userId);

        SongDto songDto = songService.findById(songId);
        UserDto userDto = userService.findById(userId);

        if (userDto.userSongs().contains(songDto)) {
            throw new SongAlreadyAddedException(
                    "User [" + userDto.id() + "] already added song [" + songDto.id() + "]");
        }

        userDto.userSongs().add(songDto);
        User user = userMapper.toEntity(userDto);

        userService.save(user);
        log.info("User {} add song with id {}", userId, songId);

        return songDto;
    }

    @DeleteMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
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