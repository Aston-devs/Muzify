import SongItem from "./SongItem";
import "./Playlist.css";

export default function Playlist({
  showSongs,
  songsLoaded,
  allSongs,
  currentSongId,
  handlePlay,
}) {
  return (
    <div>
      {showSongs && songsLoaded ? (
        <ul>
          {allSongs.map((song) => (
            <SongItem
              key={song.id}
              song={song}
              currentSongId={currentSongId}
              onPlay={handlePlay}
            />
          ))}
        </ul>
      ) : null}
    </div>
  );
}
