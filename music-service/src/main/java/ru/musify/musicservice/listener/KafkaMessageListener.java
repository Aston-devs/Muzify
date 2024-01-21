package ru.musify.musicservice.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musify.musicservice.aop.Loggable;
import ru.musify.musicservice.dto.SongMetaInfo;
import ru.musify.musicservice.dto.UserMetainfo;
import ru.musify.musicservice.entity.Author;
import ru.musify.musicservice.entity.Cover;
import ru.musify.musicservice.entity.Song;
import ru.musify.musicservice.entity.User;
import ru.musify.musicservice.service.AuthorService;
import ru.musify.musicservice.service.CoverService;
import ru.musify.musicservice.service.SongService;
import ru.musify.musicservice.service.UserService;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaMessageListener {

    private final UserService userService;

    private final SongService songService;

    private final CoverService coverService;

    private final AuthorService authorService;

    private final ObjectMapper objectMapper;

    @Loggable
    @KafkaListener(topics = "music-upload", groupId = "muzify-song")
    @Transactional(rollbackFor = JsonProcessingException.class)
    @CacheEvict(value = "songsCache", allEntries = true)
    public void listen(String metaInfo) {
        log.info("Start saving {}", metaInfo);

        try {
            SongMetaInfo songMetaInfo = objectMapper.readValue(metaInfo, SongMetaInfo.class);

            Optional<Author> optionalAuthor = Optional.ofNullable(
                    authorService.findByName(songMetaInfo.author()));
            Author author = optionalAuthor.orElseGet(() -> {
                Author newAuthor = Author.builder().name(songMetaInfo.author()).build();
                authorService.save(newAuthor);
                return newAuthor;
            });

            Cover cover = Cover.builder().url(songMetaInfo.imageUrl()).build();
            coverService.save(cover);

            Song song = Song.builder()
                    .title(songMetaInfo.title())
                    .url(songMetaInfo.audioUrl())
                    .author(author)
                    .cover(cover)
                    .build();
            songService.save(song);

            log.info("Success saved {}", metaInfo);
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: {}", metaInfo, e);
            throw new IllegalArgumentException(e);
        }
    }

    @Loggable
    @KafkaListener(topics = "user-creator", groupId = "muzify-song")
    @Transactional(rollbackFor = JsonProcessingException.class)
    public void listenUserTopic(String userMetainfo) {
        log.info("Start saving {}", userMetainfo);
        try {
            UserMetainfo metaInfo = objectMapper.readValue(userMetainfo, UserMetainfo.class);
            User user = new User();
            user.setId(UUID.fromString(metaInfo.userId()));
            userService.save(user);
            log.info("Success saved {}", metaInfo);
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: {}", userMetainfo, e);
            throw new IllegalArgumentException(e);
        }
    }
}