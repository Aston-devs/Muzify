package ru.musify.musicservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "image", schema = "music_service")
public class Image extends BaseEntity {

  @Column(name = "url", nullable = false)
  private String url;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Image image)) {
      return false;
    }
    return Objects.equals(getId(), image.getId()) && Objects.equals(url, image.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), url);
  }
}
