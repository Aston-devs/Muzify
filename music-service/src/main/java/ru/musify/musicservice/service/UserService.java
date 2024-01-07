package ru.musify.musicservice.service;

import java.util.List;
import java.util.UUID;
import ru.musify.musicservice.model.dto.SongDto;
import ru.musify.musicservice.model.dto.UserDto;
import ru.musify.musicservice.model.entity.User;

public interface UserService extends BaseService<User, UserDto> {

  boolean isUserExists(UUID id);

  List<SongDto> findSongsByUserId(UUID id);
}