package ru.musify.playerservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionThrower {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionThrower.class);

    public static void handleUploadFailure(RuntimeException exception, String message) {
        LOG.error(message);
        throw exception;
    }
}
