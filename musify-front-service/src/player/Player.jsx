import React, { useState } from "react";
import PlayBtn from "../play_btn/PlayBtn";
import "./Player.css";

export default function Player() {
  // const [volume, setVolume] = useState(50);

  // const volumeChangeHandler = (event) => {
  //   setVolume(event.target.value);
  // };

  return (
    <div className="player-container">
      <h1>Player</h1>
      <PlayBtn />
      <div className="volume-control">
        {/* <label htmlFor="volume">Volume:</label>
        <input
          type="range"
          id="volume"
          name="volume"
          min="0"
          max="100"
          value={volume}
          onChange={volumeChangeHandler}
        /> */}
      </div>
    </div>
  );
}
