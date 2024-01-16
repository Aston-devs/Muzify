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
    @MockBean
    private SongMapper songMapper;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void testFindById() {

        User user = new User();
        user.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        user.setId(UUID.randomUUID());
        user.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        user.setUserSongs(new ArrayList<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        UUID id = UUID.randomUUID();
        UserDto userDto = new UserDto(id, new ArrayList<>());

        when(userMapper.toDto(Mockito.<User>any())).thenReturn(userDto);

        UserDto actualFindByIdResult = userServiceImpl.findById(UUID.randomUUID());

        verify(userRepository).findById(Mockito.<UUID>any());
        verify(userMapper).toDto(Mockito.<User>any());
        assertSame(userDto, actualFindByIdResult);
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

        User user = new User();
        user.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        user.setId(UUID.randomUUID());
        user.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        user.setUserSongs(new ArrayList<>());
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);
        UUID id = UUID.randomUUID();
        UserDto userDto = new UserDto(id, new ArrayList<>());

        when(userMapper.toDto(Mockito.<User>any())).thenReturn(userDto);

        User user2 = new User();
        user2.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        user2.setId(UUID.randomUUID());
        user2.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        user2.setUserSongs(new ArrayList<>());

        UserDto actualSaveResult = userServiceImpl.save(user2);

        verify(userRepository).save(Mockito.<User>any());
        verify(userMapper).toDto(Mockito.<User>any());
        assertSame(userDto, actualSaveResult);
    }

    @Test
    void testUpdate() {

        User user = new User();
        user.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        user.setId(UUID.randomUUID());
        user.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        user.setUserSongs(new ArrayList<>());
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);
        UUID id = UUID.randomUUID();
        UserDto userDto = new UserDto(id, new ArrayList<>());

        when(userMapper.toDto(Mockito.<User>any())).thenReturn(userDto);

        User user2 = new User();
        user2.setCreatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        user2.setId(UUID.randomUUID());
        user2.setUpdatedAt(LocalDate.of(2020, 1, 1).atStartOfDay());
        user2.setUserSongs(new ArrayList<>());

        UserDto actualUpdateResult = userServiceImpl.update(user2);

        verify(userRepository).save(Mockito.<User>any());
        verify(userMapper).toDto(Mockito.<User>any());
        assertSame(userDto, actualUpdateResult);
    }

    @Test
    void testRemoveById() {

        doNothing().when(userRepository).deleteById(Mockito.<UUID>any());

        userServiceImpl.removeById(UUID.randomUUID());

        verify(userRepository).deleteById(Mockito.<UUID>any());
    }

    @Test
    void testIsUserExists() {

        when(userRepository.existsById(Mockito.<UUID>any())).thenReturn(true);

        boolean actualIsUserExistsResult = userServiceImpl.isUserExists(UUID.randomUUID());

        verify(userRepository).existsById(Mockito.<UUID>any());
        assertTrue(actualIsUserExistsResult);
    }

    @Test
    void testFindSongsByUserId() {

        when(userRepository.findSongsByUserId(Mockito.<UUID>any())).thenReturn(new ArrayList<>());

        List<SongDto> actualFindSongsByUserIdResult = userServiceImpl.findSongsByUserId(UUID.randomUUID());

        verify(userRepository).findSongsByUserId(Mockito.<UUID>any());
        assertTrue(actualFindSongsByUserIdResult.isEmpty());
    }
}