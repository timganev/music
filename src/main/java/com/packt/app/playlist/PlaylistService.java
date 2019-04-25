package com.packt.app.playlist;

public interface PlaylistService {
    Iterable<Playlist> getPlaylists();
    void save(Playlist playlist);
    void generatePlaylist(String title, String userName, double duration);

}
