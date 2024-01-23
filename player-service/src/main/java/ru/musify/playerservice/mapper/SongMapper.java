package ru.musify.playerservice.mapper;

import org.mapstruct.Mapper;
import ru.musify.playerservice.dto.SongMetainfo;
import ru.musify.playerservice.dto.SongRequest;

/**
 * This interface provides mapping methods for converting between SongRequest DTOs and SongMetainfo entities.
 */
@Mapper(componentModel = "spring")
public interface SongMapper {

    /**
     * Converts a SongRequest DTO to a SongMetainfo entity.
     * @param dto The SongRequest DTO to be converted.
     * @return The corresponding SongMetainfo entity.
     */
    SongMetainfo toMetainfo(SongRequest dto);
}