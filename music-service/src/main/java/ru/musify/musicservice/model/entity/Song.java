package ru.musify.musicservice.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "song", schema = "music_service")
public class Song extends BaseEntity {

  @Column(name = "title", nullable = false, length = 150)
  private String title;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private Author author;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cover_id", unique = true)
  private Cover cover;

  @Column(name = "duration", nullable = false, columnDefinition = "TIME")
  private Time duration;

  @Column(name = "genre")
  @Enumerated(EnumType.STRING)
  private Genre genre;

  @Column(name = "album", length = 150)
  private String album;

  @Column(name = "url", nullable = false)
  private String url;

  @Column(name = "release_date")
  private LocalDateTime releaseDate;

  @ManyToMany(mappedBy = "userSongs")
  private List<User> users;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Song song)) {
      return false;
    }
    return Objects.equals(getId(), ((Song) o).getId()) && Objects.equals(title, song.title)
        && Objects.equals(author,
        song.author) && Objects.equals(cover, song.cover) && Objects.equals(
        duration, song.duration) && genre == song.genre && Objects.equals(album, song.album)
        && Objects.equals(url, song.url) && Objects.equals(releaseDate,
        song.releaseDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, author, cover, duration, genre, album, url, releaseDate, getId());
  }
}
