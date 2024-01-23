package ru.musify.musicservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.musify.musicservice.entity.Cover;

/**
 * CoverRepository interface for accessing and managing Cover entities in the database.
 */
@Repository
public interface CoverRepository extends JpaRepository<Cover, UUID> {

}