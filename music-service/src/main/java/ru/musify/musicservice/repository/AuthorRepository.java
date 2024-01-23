package ru.musify.musicservice.repository;

import java.util.UUID;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.musify.musicservice.entity.Author;

/**
 * AuthorRepository interface for accessing and managing Author entities in the database.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {

    /**
     * Deletes an author by their ID.
     *
     * @param id The ID of the author to be deleted.
     */
    @Modifying
    @Query("delete from Author a where a.id = :id")
    void deleteById(@Param("id") @NonNull UUID id);

    /**
     * Finds an author by their name.
     *
     * @param name The name of the author to be found.
     * @return The author with the specified name.
     */
    Author findAuthorByName(@NonNull String name);
}