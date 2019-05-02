package com.packt.app.GeneratePlaylist;

import com.packt.app.playlist.Playlist;
import com.packt.app.playlist.PlaylistCredentialsList;
import com.packt.app.track.Track;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface GeneratePlaylistService {
    void generatePlaylistByOneGenre(HttpServletRequest req, HttpServletResponse res)throws IOException;
    void generatePlaylistByMoreGenres(HttpServletRequest req, HttpServletResponse res)throws IOException ;
    PlaylistCredentialsList generate(HttpServletRequest req, HttpServletResponse res)throws IOException;

}
