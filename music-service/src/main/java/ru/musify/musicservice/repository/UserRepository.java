package ru.musify.musicservice.repository;

import java.util.List;
import java.util.UUID;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.musify.musicservice.entity.Song;
import ru.musify.musicservice.entity.User;

/**
 * UserRepository interface for accessing and managing User entities in the database.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to be deleted.
     */
    @Modifying
    @Query("delete from User u where u.id = :id")
    void deleteById(@Param("id") @NonNull UUID id);

    /**
     * Checks if a user with the specified ID exists.
     *
     * @param id The ID of the user to check for existence.
     * @return True if a user with the specified ID exists, false otherwise.
     */
    @Override
    boolean existsById(@NonNull UUID id);

    /**
     * Retrieves the songs associated with a user by their ID.
     *
     * @param userId The ID of the user.
     * @return A list of songs associated with the user.
     */
    @Query("SELECT u.userSongs FROM User u WHERE u.id = :userId")
    List<Song> findSongsByUserId(@Param("userId") @NonNull UUID userId);
}