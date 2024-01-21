import React, { useEffect, useState } from "react";
import Playlist from "../playlist/Playlist";
import axios from "axios";
import "./MusicBar.css";

export default function MusicBar() {
  const [userId, setUserId] = useState("");
  const [allSongs, setAllSongs] = useState([]);
  const [jwtToken, setJwtToken] = useState("");
  const [userSongs, setUserSongs] = useState("");
  const [showSongs, setShowSongs] = useState(false);
  const [songsLoaded, setSongsLoaded] = useState(false);
  const [activeButton, setActiveButton] = useState(null);
  const [currentSongId, setCurrentSongId] = useState(null);

  useEffect(() => {
    const storedToken = localStorage.getItem("jwtToken");
    if (storedToken) {
      setJwtToken(storedToken);
    }
  }, []);

  useEffect(() => {
    const storedId = localStorage.getItem("userId");
    if (storedId) {
      setUserId(storedId);
    }
  }, []);

  const loadAllSongs = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/v1/musify/audios",
        {
          headers: {
            Authorization: `Bearer ${jwtToken}`,
          },
        }
      );
      const data = response.data;
      setAllSongs(data);
      setSongsLoaded(true);
    } catch (error) {
      console.error("Ошибка при получении всех песен:", error);
    }
  };

  const loadUserSongs = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/musify/audios/${userId}`,
        {
          headers: {
            Authorization: `Bearer ${jwtToken}`,
          },
        }
      );
      const data = response.data;
      setUserSongs(data);
      setSongsLoaded(true);
    } catch (error) {
      console.error("Ошибка при получении песен пользователя:", error);
    }
  };

  const handleClick = (button) => {
    setActiveButton(button);
    if (button === "news") {
      loadAllSongs();
      setShowSongs(true);
    } else if (button === "myMusic") {
      loadUserSongs();
      setShowSongs(true);
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
          allSongs={activeButton === "news" ? allSongs : userSongs}
          currentSongId={currentSongId}
          handlePlay={handlePlay}
        />
      </div>
    </>
  );
}
