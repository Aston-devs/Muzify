import React, { useState, useEffect } from "react";
import axios from "axios";

export default function AddTrackBtn({ currentPlaylist, songId }) {
  const [userId, setUserId] = useState("");
  const [jwtToken, setJwtToken] = useState("");
  const [buttonText, setButtonText] = useState("Add");

  useEffect(() => {
    if (currentPlaylist === "user") {
      setButtonText("Remove");
    }
  }, [currentPlaylist]);

  useEffect(() => {
    const storedId = localStorage.getItem("userId");
    if (storedId) {
      setUserId(storedId);
    }
  }, []);

  useEffect(() => {
    const storedToken = localStorage.getItem("jwtToken");
    if (storedToken) {
      setJwtToken(storedToken);
    }
  }, []);

  const clickHandler = async () => {
    try {
      if (buttonText === "Add") {
        await axios.patch(
          `http://localhost:8080/api/v1/musify/audios/${userId}?trackId=${songId}`,
          null,
          {
            headers: {
              Authorization: `Bearer ${jwtToken}`,
            },
          }
        );
        setButtonText("Added");
      } else if (buttonText === "Remove") {
        await axios.delete(
          `http://localhost:8080/api/v1/musify/audios/${userId}?trackId=${songId}`,
          {
            headers: {
              Authorization: `Bearer ${jwtToken}`,
            },
          }
        );
        setButtonText("Removed");
      }
    } catch (error) {
      console.error("Ошибка при отправке запроса:", error);
    }
  };

  return (
    <div className="play-btn" onClick={clickHandler}>
      {buttonText}
    </div>
  );
}
