import React, { useState } from "react";
import "./MusicBar.css";

export default function MusicBar() {
  const [activeButton, setActiveButton] = useState(null);

  const handleClick = (button) => {
    setActiveButton(button);
  };

  return (
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
  );
}
