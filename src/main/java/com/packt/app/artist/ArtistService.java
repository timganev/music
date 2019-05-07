package com.packt.app.artist;

import org.hibernate.validator.constraints.URL;

import java.util.List;

public interface ArtistService  {
    List<Artist> getArtists();
    void saveArtist(Artist artist);
}
