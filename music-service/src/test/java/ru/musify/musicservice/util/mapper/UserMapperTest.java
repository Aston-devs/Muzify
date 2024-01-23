package ru.musify.musicservice.util.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;;
import ru.musify.musicservice.dto.AuthorDto;
import ru.musify.musicservice.dto.CoverDto;
import ru.musify.musicservice.dto.ImageDto;
import ru.musify.musicservice.dto.SongDto;
import ru.musify.musicservice.dto.UserDto;
import ru.musify.musicservice.entity.Author;
import ru.musify.musicservice.entity.Cover;
import ru.musify.musicservice.entity.Genre;
import ru.musify.musicservice.entity.Song;
import ru.musify.musicservice.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private UserDto expUserDto;
    private User expUser;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {

        expUser = new User();
        expUser.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        expUser.setId(UUID.randomUUID());
        expUser.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        expUser.setUserSongs(new ArrayList<>());

        expUserDto = new UserDto(UUID.randomUUID(), new ArrayList<>());
    }

    @Test
    void testToEntity() {

        when(userMapper.toEntity(expUserDto)).thenReturn(expUser);
        User actual = userMapper.toEntity(expUserDto);
        assertEquals(expUser, actual);

    }

    @Test
    void testToDto() {

        when(userMapper.toDto(expUser)).thenReturn(expUserDto);
        UserDto actual = userMapper.toDto(expUser);
        assertEquals(expUserDto, actual);

    }
}
