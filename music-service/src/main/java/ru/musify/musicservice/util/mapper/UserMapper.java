package ru.musify.musicservice.util.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import ru.musify.musicservice.dto.UserDto;
import ru.musify.musicservice.entity.User;

/**
 * UserMapper interface for mapping between User and UserDto objects.
 */
@Mapper(componentModel = SPRING, uses = {SongMapper.class}, unmappedTargetPolicy = IGNORE)
public interface UserMapper {

    /**
     * Maps a UserDto object to a User entity.
     *
     * @param dto The UserDto object to be mapped.
     * @return The mapped User entity.
     */
    User toEntity(UserDto dto);

    /**
     * Maps a User entity to a UserDto object.
     *
     * @param user The User entity to be mapped.
     * @return The mapped UserDto object.
     */
    UserDto toDto(User user);
}