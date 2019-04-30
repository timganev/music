package com.packt.app.playlist;

import com.packt.app.track.Track;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface PlaylistService {
    Iterable<Playlist> getPlaylists();
    void save(Playlist playlist);
    void generatePlaylistByOneGenre(HttpServletRequest req, HttpServletResponse res)throws IOException ;
    void generatePlaylistByMoreGenres(HttpServletRequest req, HttpServletResponse res)throws IOException ;
    PlaylistCredentialsList generate(HttpServletRequest req, HttpServletResponse res)throws IOException;
    List<Playlist> getPlaylistByTitle(String title);
    Set<Track> getPlaylistTracks(String title);
    List<Playlist> findByUserId_Username(String username);

//    PlaylistCredentialsList generate(HttpServletRequest req, HttpServletResponse res);

}
