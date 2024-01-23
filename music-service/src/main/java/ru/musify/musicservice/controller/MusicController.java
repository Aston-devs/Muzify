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

/**
 * Controller class for managing music-related operations.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/musify/audios")
public class MusicController {

    /**
     * Service for managing songs.
     */
    private final SongService songService;

    /**
     * Service for managing users.
     */
    private final UserService userService;

    /**
     * Mapper for converting UserDto objects to User entities and vice versa.
     */
    private final UserMapper userMapper;

    /**
     * Retrieves all songs with pagination.
     * @param page - the page number
     * @param size - the page size
     * @return a list of SongDto objects
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SongDto> getAllSongs(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size) {
        return songService.findPaginatedSongs(page, size);
    }

    /**
     * Retrieves songs for a specific user.
     * @param userId - the ID of the user
     * @return a UserSongsDto object containing the user's songs
     */
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

    /**
     * Adds a song to a user's collection.
     * @param userId - the ID of the user
     * @param songId - the ID of the song to add
     * @return the added SongDto object
     */
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

    /**
     * Removes a song from a user's collection.
     * @param userId - the ID of the user
     * @param songId - the ID of the song to remove
     * @return a ResponseData object indicating the status of the operation
     */
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

    /**
     * Checks if a user exists.
     * @param id - the ID of the user
     */
    public void isUserExists(UUID id) {
        if (!userService.isUserExists(id)) {
            log.debug("User does not exist with id {}", id);
            throw new UserNotExistException("User does not exist with id " + id);
        }
    }
}