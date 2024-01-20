package ru.musify.musicservice.repository;

import java.util.UUID;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.musify.musicservice.entity.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, UUID> {

    @Override
    Page<Song> findAll(@NonNull Pageable pageable);
}
