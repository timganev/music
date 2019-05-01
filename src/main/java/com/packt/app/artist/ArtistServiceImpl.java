package com.packt.app.artist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class ArtistServiceImpl implements ArtistService {

    private ArtistRepository artistRepository;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Iterable<Artist> getArtists(){

        return artistRepository.findAll();
    }
    public void saveArtist(Artist artist){
        artistRepository.save(artist);
    }
}
