package ru.musify.musicservice.service;

import java.util.List;
import java.util.UUID;

import ru.musify.musicservice.dto.SongDto;
import ru.musify.musicservice.dto.UserDto;
import ru.musify.musicservice.entity.User;

/**
 * UserService interface for providing service methods related to User entities.
 */
public interface UserService extends BaseService<User, UserDto> {

    /**
     * Checks if a user with the specified ID exists.
     *
     * @param id The ID of the user to be checked.
     * @return true if the user exists, false otherwise.
     */
    boolean isUserExists(UUID id);

    /**
     * Retrieves all songs associated with a user by their ID.
     *
     * @param id The ID of the user.
     * @return A list of songs (SongDto).
     */
    List<SongDto> findSongsByUserId(UUID id);
}