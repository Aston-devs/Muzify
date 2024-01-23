import Header from "../header/Header";
import Player from "../player/Player";
import MusicBar from "../music bar/MusicBar";

export default function Home() {
  return (
    <div className="App">
      <header className="App-header">
        <Header />
        <Player />
        <MusicBar />
      </header>
    </div>
  );
}
