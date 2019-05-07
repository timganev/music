package com.packt.app.album;

import java.util.List;

public interface AlbumService {
    List<Album> getAlbums();
    void saveAlbums(Album album);
}
