package ru.musify.musicservice.util.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import ru.musify.musicservice.model.dto.CoverDto;
import ru.musify.musicservice.model.entity.Cover;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface CoverMapper {

  Cover toEntity(CoverDto dto);

  CoverDto toDto(Cover cover);
}
