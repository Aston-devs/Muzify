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
@Table(name = "cover", schema = "music_service")
public class Cover extends BaseEntity {

  @Column(name = "url", nullable = false)
  private String url;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Cover cover)) {
      return false;
    }
    return Objects.equals(getId(), cover.getId()) && Objects.equals(url, cover.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), url);
  }
}
