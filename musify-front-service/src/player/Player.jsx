import React, { useState } from "react";
import "./Player.css";

export default function Player() {
  const [isPlaying, setIsPlaying] = useState(false);
  const [currentSong, setCurrentSong] = useState(null);

  const playPauseHandler = () => {
    setIsPlaying(!isPlaying);
    // Отправить запрос на бэкенд для включения/выключения музыки
    if (currentSong) {
      // Отправить запрос на бэкенд с информацией о воспроизводимой песне
    }
  };

  return (
    <div className="player-container">
      <h1>My Player</h1>
      <div className="player" onClick={playPauseHandler}>
        {isPlaying ? "Pause" : "Play"}
      </div>
    </div>
  );
}
