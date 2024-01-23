package ru.musify.musicservice.util.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import ru.musify.musicservice.dto.CoverDto;
import ru.musify.musicservice.entity.Cover;

/**
 * CoverMapper interface for mapping between Cover and CoverDto objects.
 */
@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface CoverMapper {

    /**
     * Maps a CoverDto object to a Cover entity.
     *
     * @param dto The CoverDto object to be mapped.
     * @return The mapped Cover entity.
     */
    Cover toEntity(CoverDto dto);

    /**
     * Maps a Cover entity to a CoverDto object.
     *
     * @param cover The Cover entity to be mapped.
     * @return The mapped CoverDto object.
     */
    CoverDto toDto(Cover cover);
}