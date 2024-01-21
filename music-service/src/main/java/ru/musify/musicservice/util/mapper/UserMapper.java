package ru.musify.musicservice.util.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import ru.musify.musicservice.dto.UserDto;
import ru.musify.musicservice.entity.User;

@Mapper(componentModel = SPRING, uses = {SongMapper.class}, unmappedTargetPolicy = IGNORE)
public interface UserMapper {

    User toEntity(UserDto dto);

    UserDto toDto(User user);
}
