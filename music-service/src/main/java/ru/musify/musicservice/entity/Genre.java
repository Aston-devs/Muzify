package ru.musify.musicservice.entity;

import lombok.Getter;

@Getter
public enum Genre {
  CLASSICAL("Classical"),
  ELECTRONIC("Electronic"),
  ROCK("Rock"),
  HIP_HOP("Hip-Hop"),
  RAP("Rap"),
  POP("Pop"),
  JAZZ("Jazz"),
  INDIE("Indie"),
  HOUSE("House"),
  ALTERNATIVE("Alternative"),
  RB("R&B"),
  TECHNO("Techno");

  private final String name;

  Genre(String name) {
    this.name = name;
  }
}
