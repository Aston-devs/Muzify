package ru.musify.musicservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "author", schema = "music_service")
public class Author extends BaseEntity {

  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  @Column(name = "genre")
  @Enumerated(value = EnumType.STRING)
  private Genre genre;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "photo_id", unique = true)
  private Image photo;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Author author)) {
      return false;
    }
    return Objects.equals(getId(), author.getId()) && Objects.equals(name, author.name)
        && genre == author.genre
        && Objects.equals(photo, author.photo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), name, genre, photo);
  }
}
