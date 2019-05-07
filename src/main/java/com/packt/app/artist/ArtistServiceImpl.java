package com.packt.app.artist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {

    private ArtistRepository artistRepository;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> getArtists(){
        return (List<Artist>) artistRepository.findAll();
    }
    public void saveArtist(Artist artist){
        artistRepository.save(artist);
    }
}
