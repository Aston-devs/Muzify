package ru.musify.musicservice.util.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.musify.musicservice.model.dto.NewSongDto;
import ru.musify.musicservice.model.dto.SongDto;
import ru.musify.musicservice.model.entity.Song;

@Mapper(componentModel = SPRING, uses = {AuthorMapper.class,
    CoverMapper.class}, unmappedTargetPolicy = IGNORE)
public interface SongMapper {

  Song toEntity(SongDto dto);

  SongDto toDto(Song song);

  @Mapping(source = "songUrl", target = "url")
  @Mapping(target = "cover", ignore = true)
  @Mapping(target = "author", ignore = true)
  Song toEntity(NewSongDto newSongDto);
}
