import React, { useEffect, useState, useRef } from "react";
import AddTrackBtn from "../play_btn/AddTrackBtn";
import PlayBtn from "../play_btn/PlayBtn";
import "./SongItem.css";

export default function SongItem({
  song,
  currentSongId,
  onPlay,
  currentPlaylist,
}) {
  const audioRef = useRef(null);
  const [isPlaying, setIsPlaying] = useState(false);

  useEffect(() => {
    setIsPlaying(currentSongId === song.id);
  }, [currentSongId, song.id]);

  useEffect(() => {
    if (isPlaying) {
      audioRef.current.play().catch((error) => {
        console.error("Ошибка при воспроизведении аудио:", error);
      });
    } else {
      audioRef.current.pause();
    }
  }, [isPlaying]);

  const handlePlay = () => {
    onPlay(song.id);
  };

  return (
    <li className="song-item" key={song.id}>
      <div className="song-info">
        <h3 className="song-title">{song.title}</h3>
        <p className="song-author">By {song.author.name}</p>
      </div>
      <div className="song-controls">
        <PlayBtn handlePlay={handlePlay} isPlaying={isPlaying} />
        <AddTrackBtn currentPlaylist={currentPlaylist} songId={song.id} />
      </div>
      <audio
        ref={audioRef}
        src={`http://localhost:8080/api/v1/player/play/${song.url}`}
        preload="none"
        onPlay={() => setIsPlaying(true)}
        onPause={() => setIsPlaying(false)}
      >
        Your browser does not support the audio element.
      </audio>
    </li>
  );
}
