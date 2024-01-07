package ru.musify.musicservice.repository;

import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.musify.musicservice.model.entity.Song;
import ru.musify.musicservice.model.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

  @Modifying
  @Query("delete from User u where u.id = :id")
  void deleteById(@NonNull UUID id);

  @Override
  boolean existsById(@NonNull UUID id);

  @Query("select Song from User u where u.id = :id")
  List<Song> findSongsByUserId(@NonNull UUID id);
}
