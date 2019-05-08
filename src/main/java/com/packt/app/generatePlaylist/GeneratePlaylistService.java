package com.packt.app.generatePlaylist;

import com.packt.app.playlist.Playlist;
import com.packt.app.playlist.PlaylistCredentials;
import com.packt.app.playlist.PlaylistCredentialsList;
import com.packt.app.track.Track;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface GeneratePlaylistService {

    void generatePlaylist(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException;

}
