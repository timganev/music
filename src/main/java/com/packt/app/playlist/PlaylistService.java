package com.packt.app.playlist;

import com.packt.app.track.Track;

import java.util.List;
import java.util.Set;

public interface PlaylistService {
    List<Playlist> getPlaylists();
    void save(Playlist playlist);
    Playlist getPlaylistByTitle(String title);
    Set<Track> getPlaylistTracks(String title);


}
