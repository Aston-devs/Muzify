package ru.musify.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.musify.userservice.dto.SignUpRequest;
import ru.musify.userservice.model.User;

/**
 * This interface represents a mapper for converting SignUpRequest DTOs to User entities.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Maps a SignUpRequest DTO to a User entity, ignoring the password field.
     *
     * @param dto The SignUpRequest DTO to be mapped.
     * @return The User entity mapped from the DTO.
     */
    @Mapping(target = "password", ignore = true)
    User toUser(SignUpRequest dto);

}