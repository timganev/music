package com.packt.app.GeneratePlaylist;

import com.packt.app.playlist.Playlist;
import com.packt.app.playlist.PlaylistCredentials;
import com.packt.app.playlist.PlaylistCredentialsList;
import com.packt.app.track.Track;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface GeneratePlaylistService {
    Playlist generatePlaylistByOneGenre(HttpServletRequest req, HttpServletResponse res) throws IOException;
    void saveGeneratedPlaylistByOneGenre(HttpServletRequest req, HttpServletResponse res) throws IOException;
    void saveGeneratedPlaylistByMoreGenre(HttpServletRequest req, HttpServletResponse res) throws IOException;
    Playlist generatePlaylistByMoreGenres(HttpServletRequest req, HttpServletResponse res)throws IOException ;
    PlaylistCredentialsList generate(HttpServletRequest req, HttpServletResponse res)throws IOException;
    double getCurrentDuration(double currentDuration, Playlist playlist, int countOfRandomReturns,
                              PlaylistCredentials pl, String genre);

    void saveGeneratedPlaylist(HttpServletRequest req, HttpServletResponse res)throws IOException;

    Set<Track> getTracksByGenreOrderedByRankDesc(String genre);

}
