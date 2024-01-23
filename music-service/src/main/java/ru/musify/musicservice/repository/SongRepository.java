package ru.musify.musicservice.repository;

import java.util.UUID;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.musify.musicservice.entity.Song;

/**
 * SongRepository interface for accessing and managing Song entities in the database.
 */
@Repository
public interface SongRepository extends JpaRepository<Song, UUID> {

    /**
     * Retrieves all songs with pagination.
     *
     * @param pageable The pagination information.
     * @return A page of songs.
     */
    @Override
    @EntityGraph(attributePaths = {"author", "cover", "users"})
    Page<Song> findAll(@NonNull Pageable pageable);
}
