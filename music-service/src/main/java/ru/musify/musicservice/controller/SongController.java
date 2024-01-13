package ru.musify.musicservice.controller;

import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.musify.musicservice.handler.ResponseData;
import ru.musify.musicservice.model.dto.NewSongDto;
import ru.musify.musicservice.model.dto.SongDto;
import ru.musify.musicservice.model.entity.Author;
import ru.musify.musicservice.model.entity.Cover;
import ru.musify.musicservice.model.entity.Song;
import ru.musify.musicservice.service.AuthorService;
import ru.musify.musicservice.service.SongService;
import ru.musify.musicservice.util.mapper.SongMapper;

@Slf4j
@RestController
@RequestMapping("/api/v1/musify/songs")
public class SongController {

  private final SongService songService;

  private final AuthorService authorService;

  private final SongMapper songMapper;

  @Autowired
  public SongController(SongService songService, AuthorService authorService,
      SongMapper songMapper) {
    this.songService = songService;
    this.authorService = authorService;
    this.songMapper = songMapper;
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(code = HttpStatus.CREATED)
  public SongDto addNewSong(@RequestBody @Valid NewSongDto newSongDto,
      @AuthenticationPrincipal UserDetails userDetails) {
    Song song = songMapper.toEntity(newSongDto);

    String coverUrl = newSongDto.coverUrl();
    String authorName = newSongDto.author();


    if (isNotBlank(coverUrl)) {
      song.setCover(new Cover(coverUrl));
    }

    if (isNotBlank(authorName)) {
      Author author = authorService.findByName(authorName);
      song.setAuthor(author);
    }

    SongDto savedSong = songService.save(song);

    log.info("Admin [{}] added new song {}", userDetails.getUsername(), savedSong.id());

    return savedSong;
  }

  @DeleteMapping("/{songId}")
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(code = HttpStatus.OK)
  public ResponseData removeSong(@PathVariable UUID songId,
      @AuthenticationPrincipal UserDetails userDetails) {
    SongDto songDto = songService.findById(songId);

    if (songDto == null) {
      return ResponseData.builder()
          .statusCode(SC_NOT_FOUND)
          .message("Song not found with id " + songId)
          .build();
    }

    songService.removeById(songId);
    log.info("Admin [{}] removed song {}", userDetails.getUsername(), songId);

    return ResponseData.builder()
        .statusCode(SC_OK)
        .message("Song successfully removed")
        .build();
  }
}
