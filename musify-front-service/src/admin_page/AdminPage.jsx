import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./AdminPage.css";

export default function AdminPage() {
  const navigate = useNavigate();
  const [title, setTitle] = useState("");
  const [author, setAuthor] = useState("");
  const [jwtToken, setJwtToken] = useState("");
  const [audioFile, setAudioFile] = useState(null);
  const [imageFile, setImageFile] = useState(null);
  const [buttonState, setButtonState] = useState("Upload");

  useEffect(() => {
    const storedToken = localStorage.getItem("jwtToken");
    if (storedToken) {
      setJwtToken(storedToken);
    }
  }, []);

  const handleImageChange = (event) => {
    setImageFile(event.target.files[0]);
  };

  const handleAudioChange = (event) => {
    setAudioFile(event.target.files[0]);
  };

  const handleTitleChange = (event) => {
    setTitle(event.target.value);
  };

  const handleAuthorChange = (event) => {
    setAuthor(event.target.value);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const formData = new FormData();
    formData.append("image", imageFile);
    formData.append("audio", audioFile);
    formData.append("title", title);
    formData.append("author", author);

    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/admin/upload",
        formData,
        {
          headers: {
            Authorization: `Bearer ${jwtToken}`,
          },
        }
      );
      console.log(response.status);
      if (response.status === 200) {
        setButtonState("Success");
      }
      console.log(response.data);
    } catch (error) {
      console.error("Ошибка при отправке запроса:", error);
    }
  };

  const clickDashboardHandler = () => {
    navigate("/dashboard");
  };

  return (
    <form className="file-upload-form" onSubmit={handleSubmit}>
      <h2 className="welcome">Hello, admin</h2>
      <div className="form-group">
        <label htmlFor="image">Select Image:</label>
        <input
          type="file"
          id="image"
          accept="image/*"
          onChange={handleImageChange}
        />
      </div>
      <div className="form-group">
        <label htmlFor="audio">Select Audio:</label>
        <input
          type="file"
          id="audio"
          accept="audio/*"
          onChange={handleAudioChange}
        />
      </div>
      <div className="form-group">
        <label className="submit-label" htmlFor="text1">
          Title:
        </label>
        <input
          className="submit-input"
          type="text"
          id="title"
          value={title}
          onChange={handleTitleChange}
        />
      </div>
      <div className="form-group">
        <label className="submit-label" htmlFor="text2">
          Author:
        </label>
        <input
          className="submit-input"
          type="text"
          id="author"
          value={author}
          onChange={handleAuthorChange}
        />
      </div>
      <div className="buttons">
        <button className="submit-btn" type="submit">
          {buttonState}
        </button>
        <button className="submit-btn" onClick={clickDashboardHandler}>
          Dashboard
        </button>
      </div>
    </form>
  );
}
