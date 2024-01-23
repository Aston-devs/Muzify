import React, { useState, useEffect } from "react";
import "./PlayBtn.css";

export default function PlayBtn({ handlePlay, isPlaying }) {
  const [buttonText, setButtonText] = useState("Play");

  useEffect(() => {
    setButtonText(isPlaying ? "Pause" : "Play");
  }, [isPlaying]);

  const playPauseHandler = () => {
    handlePlay();
  };

  return (
    <div className="play-btn" onClick={playPauseHandler}>
      {buttonText}
    </div>
  );
}
