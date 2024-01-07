package ru.musify.musicservice.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musify.musicservice.handler.exception.EntityNotFoundException;
import ru.musify.musicservice.handler.exception.UserNotExistException;
import ru.musify.musicservice.model.dto.SongDto;
import ru.musify.musicservice.model.dto.UserDto;
import ru.musify.musicservice.model.entity.Song;
import ru.musify.musicservice.model.entity.User;
import ru.musify.musicservice.repository.UserRepository;
import ru.musify.musicservice.service.UserService;
import ru.musify.musicservice.util.mapper.SongMapper;
import ru.musify.musicservice.util.mapper.UserMapper;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository repository;

  private final UserMapper userMapper;

  private final SongMapper songMapper;

  @Autowired
  public UserServiceImpl(UserRepository repository, UserMapper userMapper, SongMapper songMapper) {
    this.repository = repository;
    this.userMapper = userMapper;
    this.songMapper = songMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDto findById(UUID id) {
    User user = repository.findById(id).orElseThrow(() -> {
      log.debug("Could not find user with id {}", id);
      return new EntityNotFoundException("User not found with id " + id);
    });
    log.info("Song found with id {}", id);

    return userMapper.toDto(user);
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserDto> findAll() {
    List<User> allSongs = repository.findAll();
    log.info("Found all users");

    return allSongs.stream()
        .map(userMapper::toDto)
        .toList();
  }

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

  @Override
  @Transactional
  public UserDto update(User user) {
    User savedUser = repository.save(user);
    log.info("Updated user with id {}", savedUser.getId());

    return userMapper.toDto(savedUser);
  }

  @Override
  @Transactional
  public void removeById(UUID id) {
    repository.deleteById(id);

    log.info("Removed user with id {}", id);
  }

  @Override
  public boolean isUserExists(UUID id) {
    return repository.existsById(id);
  }

  @Override
  public List<SongDto> findSongsByUserId(UUID id) {
    List<Song> userSongs = repository.findSongsByUserId(id);

    return userSongs.stream()
        .map(songMapper::toDto)
        .toList();
  }
}
