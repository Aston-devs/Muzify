package ru.musify.playerservice.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionThrower {

    public static void handleUploadFailure(RuntimeException e) {
        log.error(e.getMessage());
        throw e;
    }
}
