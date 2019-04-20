package com.packt.app.artist;

public interface ArtistService  {
    Iterable<Artist> getArtists();
    void saveArtist(Artist artist);
}
