package ru.musify.musicservice.service;

import java.util.List;
import java.util.UUID;
import ru.musify.musicservice.dto.SongDto;
import ru.musify.musicservice.dto.UserDto;
import ru.musify.musicservice.entity.User;

public interface UserService extends BaseService<User, UserDto> {

  boolean isUserExists(UUID id);

  List<SongDto> findSongsByUserId(UUID id);
}