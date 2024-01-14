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

public interface UserRepository extends JpaRepository<User, UUID> {

  @Modifying
  @Query("delete from User u where u.id = :id")
  void deleteById(@Param("id") @NonNull UUID id);

  @Override
  boolean existsById(@NonNull UUID id);

  @Query("SELECT u.userSongs FROM User u WHERE u.id = :userId")
  List<Song> findSongsByUserId(@Param("userId") @NonNull UUID userId);
}
