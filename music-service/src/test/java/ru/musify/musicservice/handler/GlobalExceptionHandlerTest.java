package ru.musify.musicservice.handler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import ru.musify.musicservice.handler.exception.EntityNotFoundException;
import ru.musify.musicservice.handler.exception.SongAlreadyAddedException;
import ru.musify.musicservice.handler.exception.UserNotExistException;

class GlobalExceptionHandlerTest {

    @Test
    void testEntityNotFoundException() {

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        EntityNotFoundException e = mock(EntityNotFoundException.class);
        when(e.getMessage()).thenReturn("Not all who wander are lost");

        globalExceptionHandler.entityNotFoundException(e);

        verify(e).getMessage();
    }

    @Test
    void testUserNotExistException() {

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        UserNotExistException e = mock(UserNotExistException.class);
        when(e.getMessage()).thenReturn("Not all who wander are lost");

        globalExceptionHandler.userNotExistException(e);

        verify(e).getMessage();
    }

    @Test
    void testSongAlreadyAddedException() {

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        SongAlreadyAddedException e = mock(SongAlreadyAddedException.class);
        when(e.getMessage()).thenReturn("Not all who wander are lost");

        globalExceptionHandler.songAlreadyAddedException(e);

        verify(e).getMessage();
    }
}
