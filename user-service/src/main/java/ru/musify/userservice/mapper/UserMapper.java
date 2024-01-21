package ru.musify.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.musify.userservice.dto.SignUpRequest;
import ru.musify.userservice.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    User toUser(SignUpRequest dto);

}
