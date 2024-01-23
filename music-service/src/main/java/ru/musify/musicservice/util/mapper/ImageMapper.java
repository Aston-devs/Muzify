package ru.musify.musicservice.util.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import ru.musify.musicservice.dto.ImageDto;
import ru.musify.musicservice.entity.Image;

/**
 * ImageMapper interface for mapping between Image and ImageDto objects.
 */
@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface ImageMapper {

    /**
     * Maps an ImageDto object to an Image entity.
     *
     * @param dto The ImageDto object to be mapped.
     * @return The mapped Image entity.
     */
    Image toEntity(ImageDto dto);

    /**
     * Maps an Image entity to an ImageDto object.
     *
     * @param image The Image entity to be mapped.
     * @return The mapped ImageDto object.
     */
    ImageDto toDto(Image image);
}