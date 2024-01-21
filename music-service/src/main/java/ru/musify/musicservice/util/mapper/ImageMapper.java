package ru.musify.musicservice.util.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import ru.musify.musicservice.dto.ImageDto;
import ru.musify.musicservice.entity.Image;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface ImageMapper {

    Image toEntity(ImageDto dto);

    ImageDto toDto(Image image);
}
