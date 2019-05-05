package com.packt.app.generatePlaylist;

import com.packt.app.playlist.Playlist;
import com.packt.app.playlist.PlaylistCredentials;
import com.packt.app.playlist.PlaylistCredentialsList;
import com.packt.app.track.Track;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface GeneratePlaylistService {

    void generatePlaylist(HttpServletRequest req, HttpServletResponse res) throws IOException;

    Playlist generatePlaylistByOneGenre(String title, String userName,
                                        List<PlaylistCredentials> playlistCredentials, boolean isSameArtistAllow1,
                                        boolean isTopRankAllow);

    Playlist generatePlaylistByMoreGenres(String title, String userName,
                                          List<PlaylistCredentials> playlistCredentials, boolean isSameArtistAllow1,
                                          boolean isTopRankAllow) ;

    Playlist generatePlaylistWithoutGenre(String title, String userName,
                                          List<PlaylistCredentials> playlistCredentials, boolean isSameArtistAllow,
                                          boolean isTopRankAllow);

    PlaylistCredentialsList generate(HttpServletRequest req, HttpServletResponse res)throws IOException;

    double getCurrentDuration(double currentDuration, Playlist playlist, int countOfRandomReturns,
                              PlaylistCredentials pl, String genre,boolean isSameArtistAllow,boolean isTopRankAllow);

    void saveGeneratedPlaylistWithoutGenre(String title, String userName,
                                           List<PlaylistCredentials> playlistCredentials, boolean isSameArtistAllow1,
                                           boolean isTopRankAllow);
    void saveGeneratedPlaylistByOneGenre(String title, String userName,
                                         List<PlaylistCredentials> playlistCredentials, boolean isSameArtistAllow1,
                                         boolean isTopRankAllow);
    void saveGeneratedPlaylistByMoreGenre(String title, String userName,
                                          List<PlaylistCredentials> playlistCredentials, boolean isSameArtistAllow1,
                                          boolean isTopRankAllow);

    List<Track> getTracksByGenreOrderedByRankDesc(String genre);

}
