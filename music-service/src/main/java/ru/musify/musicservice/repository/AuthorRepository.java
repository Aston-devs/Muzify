package ru.musify.musicservice.repository;

import java.util.UUID;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.musify.musicservice.model.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {

  @Modifying
  @Query("delete from Author a where a.id = :id")
  void deleteById(@NonNull UUID id);

  Author findAuthorByName(@NonNull String name);
}
