package ru.musify.musicservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.musify.musicservice.entity.Image;

/**
 * ImageRepository interface for accessing and managing Image entities in the database.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {

}