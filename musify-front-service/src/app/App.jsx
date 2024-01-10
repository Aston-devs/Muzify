import "./App.css";
import React from "react";
import Header from "../header/Header.jsx";
import Player from "../player/Player.jsx";
import MusicBar from "../music bar/MusicBar.jsx";

export default function App() {
  return (
    <>
      <div className="App">
        <header className="App-header">
          <Header />
          <Player />
          <MusicBar />
        </header>
      </div>
    </>
  );
}
