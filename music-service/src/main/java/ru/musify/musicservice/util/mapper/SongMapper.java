package ru.musify.musicservice.util.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import ru.musify.musicservice.dto.SongDto;
import ru.musify.musicservice.entity.Song;

@Mapper(componentModel = SPRING, uses = {AuthorMapper.class,
        CoverMapper.class}, unmappedTargetPolicy = IGNORE)
public interface SongMapper {

    Song toEntity(SongDto dto);

    SongDto toDto(Song song);
}
