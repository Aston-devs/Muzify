package ru.musify.musicservice.util.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import ru.musify.musicservice.dto.AuthorDto;
import ru.musify.musicservice.entity.Author;

/**
 * AuthorMapper interface for mapping between Author and AuthorDto objects.
 */
@Mapper(componentModel = SPRING, uses = ImageMapper.class, unmappedTargetPolicy = IGNORE)
public interface AuthorMapper {

    /**
     * Maps an AuthorDto object to an Author entity.
     *
     * @param dto The AuthorDto object to be mapped.
     * @return The mapped Author entity.
     */
    Author toEntity(AuthorDto dto);

    /**
     * Maps an Author entity to an AuthorDto object.
     *
     * @param author The Author entity to be mapped.
     * @return The mapped AuthorDto object.
     */
    AuthorDto toDto(Author author);
}