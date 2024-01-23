import SongItem from "./SongItem";
import "./Playlist.css";

export default function Playlist({
  showSongs,
  songsLoaded,
  allSongs,
  currentSongId,
  handlePlay,
  currentPlaylist,
}) {
  return (
    <div>
      {showSongs && songsLoaded && allSongs.length > 0 ? (
        <ul>
          {allSongs.map((song) => (
            <SongItem
              key={song.id}
              song={song}
              currentSongId={currentSongId}
              onPlay={handlePlay}
              currentPlaylist={currentPlaylist}
            />
          ))}
        </ul>
      ) : (
        <p className="no-songs-message">No songs available.</p>
      )}
    </div>
  );
}
