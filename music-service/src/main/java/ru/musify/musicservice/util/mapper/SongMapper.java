package ru.musify.musicservice.util.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import ru.musify.musicservice.dto.SongDto;
import ru.musify.musicservice.entity.Song;

/**
 * SongMapper interface for mapping between Song and SongDto objects.
 */
@Mapper(componentModel = SPRING, uses = {AuthorMapper.class,
        CoverMapper.class}, unmappedTargetPolicy = IGNORE)
public interface SongMapper {

    /**
     * Maps a SongDto object to a Song entity.
     *
     * @param dto The SongDto object to be mapped.
     * @return The mapped Song entity.
     */
    Song toEntity(SongDto dto);

    /**
     * Maps a Song entity to a SongDto object.
     *
     * @param song The Song entity to be mapped.
     * @return The mapped SongDto object.
     */
    SongDto toDto(Song song);
}