package ru.musify.musicservice.model.dto;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;
import org.hibernate.validator.constraints.URL;

public record ImageDto(@NotNull UUID id,
                       @URL String url) implements Serializable {

}