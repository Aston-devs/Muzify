package ru.musify.playerservice.mapper;

import org.mapstruct.Mapper;
import ru.musify.playerservice.dto.SongMetainfo;
import ru.musify.playerservice.dto.SongRequest;

@Mapper(componentModel = "spring")
public interface SongMapper {
    SongMetainfo toMetainfo(SongRequest dto);
}
