package ru.musify.musicservice.handler;

import lombok.Builder;

@Builder
public class ResponseData {

  private int statusCode;

  private String message;
}
