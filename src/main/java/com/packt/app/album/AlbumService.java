package com.packt.app.album;

public interface AlbumService {
    Iterable<Album> getAlbums();
    void saveAlbums(Album album);
}
