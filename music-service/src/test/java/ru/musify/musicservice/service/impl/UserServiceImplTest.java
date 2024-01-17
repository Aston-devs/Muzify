package ru.musify.musicservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.musify.musicservice.dto.SongDto;
import ru.musify.musicservice.dto.UserDto;
import ru.musify.musicservice.entity.User;
import ru.musify.musicservice.repository.UserRepository;
import ru.musify.musicservice.util.mapper.SongMapper;
import ru.musify.musicservice.util.mapper.UserMapper;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    private User expUser;
    private UserDto expUserDto;

    @MockBean
    private SongMapper songMapper;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setup() {

        expUser = new User();
        expUser.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        expUser.setId(UUID.randomUUID());
        expUser.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        expUser.setUserSongs(new ArrayList<>());

        expUserDto = new UserDto(UUID.randomUUID(), new ArrayList<>());

    }

    @Test
    void testFindById() {

        Optional<User> ofResult = Optional.of(expUser);

        when(userRepository.findById(Mockito.any())).thenReturn(ofResult);
        when(userMapper.toDto(Mockito.any())).thenReturn(expUserDto);

        UserDto actualFindByIdResult = userServiceImpl.findById(UUID.randomUUID());

        verify(userRepository).findById(Mockito.any());
        verify(userMapper).toDto(Mockito.any());
        assertSame(expUserDto, actualFindByIdResult);
    }

    @Test
    void testFindAll() {

        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        List<UserDto> actualFindAllResult = userServiceImpl.findAll();

        verify(userRepository).findAll();
        assertTrue(actualFindAllResult.isEmpty());
    }

    @Test
    void testSave() {

        when(userRepository.save(Mockito.any())).thenReturn(expUser);
        when(userMapper.toDto(Mockito.any())).thenReturn(expUserDto);

        UserDto actualSaveResult = userServiceImpl.save(expUser);

        verify(userRepository).save(Mockito.any());
        verify(userMapper).toDto(Mockito.any());
        assertSame(expUserDto, actualSaveResult);
    }

    @Test
    void testUpdate() {

        when(userRepository.save(Mockito.any())).thenReturn(expUser);
        when(userMapper.toDto(Mockito.any())).thenReturn(expUserDto);

        UserDto actualUpdateResult = userServiceImpl.update(expUser);

        verify(userRepository).save(Mockito.any());
        verify(userMapper).toDto(Mockito.any());
        assertSame(expUserDto, actualUpdateResult);
    }

    @Test
    void testRemoveById() {

        doNothing().when(userRepository).deleteById(Mockito.any());

        userServiceImpl.removeById(UUID.randomUUID());

        verify(userRepository).deleteById(Mockito.any());
    }

    @Test
    void testIsUserExists() {

        when(userRepository.existsById(Mockito.any())).thenReturn(true);

        boolean actualIsUserExistsResult = userServiceImpl.isUserExists(UUID.randomUUID());

        verify(userRepository).existsById(Mockito.any());
        assertTrue(actualIsUserExistsResult);
    }

    @Test
    void testFindSongsByUserId() {

        when(userRepository.findSongsByUserId(Mockito.any())).thenReturn(new ArrayList<>());

        List<SongDto> actualFindSongsByUserIdResult = userServiceImpl.findSongsByUserId(UUID.randomUUID());

        verify(userRepository).findSongsByUserId(Mockito.any());
        assertTrue(actualFindSongsByUserIdResult.isEmpty());
    }
}