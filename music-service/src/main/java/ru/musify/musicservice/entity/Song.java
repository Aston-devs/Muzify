package ru.musify.musicservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a song entity in the music service.
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Builder
@Getter
@Setter
@Entity
@Table(name = "song", schema = "music_service")
public class Song extends BaseEntity {

    @ToString.Include
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cover_id", unique = true)
    private Cover cover;

    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "release_date")
    private LocalDateTime releaseDate;

    @ManyToMany(mappedBy = "userSongs")
    private List<User> users;

}