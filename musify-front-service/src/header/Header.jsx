import React from "react";
import LoginBtn from "../loginBtn/LoginBtn";
import logo from "./musify-logo.svg";
import musify from "./Musify.svg";
import "./Header.css";

export default function Header() {
  return (
    <div className="header">
      <div className="header-container">
        <img className="musify-logo" src={logo} alt="Musify logo" />
        <LoginBtn />
      </div>
      <div className="header-musify-word">
        <img src={musify} alt="header musify word" />
      </div>
      <div className="header-tagline">
        Don't miss a day without your favourite music
      </div>
    </div>
  );
}
