package ru.musify.musicservice.util.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import ru.musify.musicservice.model.dto.SongDto;
import ru.musify.musicservice.model.entity.Song;

@Mapper(componentModel = SPRING, uses = {AuthorMapper.class,
    CoverMapper.class}, unmappedTargetPolicy = IGNORE)
public interface SongMapper {

  Song toEntity(SongDto dto);

  SongDto toDto(Song song);
}
