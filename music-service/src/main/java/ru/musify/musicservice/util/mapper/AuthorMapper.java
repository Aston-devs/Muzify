package ru.musify.musicservice.util.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import ru.musify.musicservice.dto.AuthorDto;
import ru.musify.musicservice.entity.Author;

@Mapper(componentModel = SPRING, uses = ImageMapper.class, unmappedTargetPolicy = IGNORE)
public interface AuthorMapper {

  Author toEntity(AuthorDto dto);

  AuthorDto toDto(Author author);
}
