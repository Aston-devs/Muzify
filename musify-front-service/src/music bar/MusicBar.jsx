import React, { useEffect, useState, useRef } from "react";
import Playlist from "../playlist/Playlist";
import "./MusicBar.css";

export default function MusicBar() {
  const [allSongs, setAllSongs] = useState([]);
  const [showSongs, setShowSongs] = useState(false);
  const [songsLoaded, setSongsLoaded] = useState(false);
  const [activeButton, setActiveButton] = useState(null);
  const [currentSongId, setCurrentSongId] = useState(null);

  const loadAllSongs = () => {
    fetch("http://localhost:8085/api/v1/musify/audios")
      .then((response) => response.json())
      .then((data) => {
        setAllSongs(data);
        setSongsLoaded(true);
      })
      .catch((error) =>
        console.error("Ошибка при получении всех песен:", error)
      );
  };

  const handleClick = (button) => {
    setActiveButton(button);
    if (button === "news") {
      loadAllSongs();
      setShowSongs(true);
    } else {
      setShowSongs(false);
    }
  };

  const handlePlay = (songId) => {
    if (currentSongId === songId) {
      setCurrentSongId(null);
    } else {
      setCurrentSongId(songId);
    }
  };

  useEffect(() => {
    loadAllSongs();
  }, []);

  return (
    <>
      <div className="bar-container">
        <button
          className={`music-bar-button ${
            activeButton === "news" ? "active" : ""
          }`}
          onClick={() => handleClick("news")}
        >
          News
        </button>
        <button
          className={`music-bar-button ${
            activeButton === "myMusic" ? "active" : ""
          }`}
          onClick={() => handleClick("myMusic")}
        >
          My Music
        </button>
      </div>
      <div className="playlist-container">
        <Playlist
          showSongs={showSongs}
          songsLoaded={songsLoaded}
          allSongs={allSongs}
          currentSongId={currentSongId}
          handlePlay={handlePlay}
        />
      </div>
    </>
  );
}
