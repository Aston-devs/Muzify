package ru.musify.musicservice.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musify.musicservice.aop.Loggable;
import ru.musify.musicservice.dto.SongDto;
import ru.musify.musicservice.dto.UserDto;
import ru.musify.musicservice.entity.Song;
import ru.musify.musicservice.entity.User;
import ru.musify.musicservice.handler.exception.EntityNotFoundException;
import ru.musify.musicservice.handler.exception.UserNotExistException;
import ru.musify.musicservice.repository.UserRepository;
import ru.musify.musicservice.service.UserService;
import ru.musify.musicservice.util.mapper.SongMapper;
import ru.musify.musicservice.util.mapper.UserMapper;

/**
 * UserServiceImpl class providing implementation for UserService.
 */
@Slf4j
@Loggable
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    /**
     * The repository for accessing and managing User entities in the database.
     */
    private final UserRepository repository;

    /**
     * The mapper for converting User entities to DTOs and vice versa.
     */
    private final UserMapper userMapper;

    /**
     * The mapper for converting Song entities to DTOs and vice versa.
     */
    private final SongMapper songMapper;

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to be retrieved.
     * @return The DTO representation of the user.
     * @throws EntityNotFoundException if the user with the specified ID is not found.
     */
    @Override
    public UserDto findById(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> {
            log.debug("Could not find user with id {}", id);
            return new EntityNotFoundException("User not found with id " + id);
        });
        log.info("Song found with id {}", id);

        return userMapper.toDto(user);
    }

    /**
     * Retrieves all users.
     *
     * @return A list of DTO representations of all users.
     */
    @Override
    public List<UserDto> findAll() {
        List<User> allSongs = repository.findAll();
        log.info("Found all users");

        return allSongs.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Saves a user.
     *
     * @param user The user to be saved.
     * @return The DTO representation of the saved user.
     * @throws UserNotExistException if the user does not have an ID.
     */
    @Override
    @Transactional
    public UserDto save(User user) {
        if (user.getId() == null) {
            throw new UserNotExistException("User must be have id");
        }

        User savedUser = repository.save(user);
        log.info("Saved user with id {}", savedUser.getId());

        return userMapper.toDto(savedUser);
    }

    /**
     * Updates a user.
     *
     * @param user The user to be updated.
     * @return The DTO representation of the updated user.
     */
    @Override
    @Transactional
    public UserDto update(User user) {
        User savedUser = repository.save(user);
        log.info("Updated user with id {}", savedUser.getId());

        return userMapper.toDto(savedUser);
    }

    /**
     * Removes a user by their ID.
     *
     * @param id The ID of the user to be removed.
     */
    @Override
    @Transactional
    public void removeById(UUID id) {
        repository.deleteById(id);

        log.info("Removed user with id {}", id);
    }

    /**
     * Checks if a user with the specified ID exists.
     *
     * @param id The ID of the user to be checked.
     * @return true if the user exists, false otherwise.
     */
    @Override
    public boolean isUserExists(UUID id) {
        return repository.existsById(id);
    }

    /**
     * Finds all songs associated with a user by their ID.
     *
     * @param id The ID of the user.
     * @return A list of songs (SongDto).
     */
    @Override
    public List<SongDto> findSongsByUserId(UUID id) {
        List<Song> userSongs = repository.findSongsByUserId(id);
        log.info("Found {} songs with user id {}", userSongs.size(), id);

        return userSongs.stream()
                .map(songMapper::toDto)
                .collect(Collectors.toList());
    }
}