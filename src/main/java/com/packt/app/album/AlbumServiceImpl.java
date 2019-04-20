package com.packt.app.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumServiceImpl implements AlbumService{

    private AlbumRepository albumRepository;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public Iterable<Album> getAlbums(){
        return albumRepository.findAll();
    }
    public void saveAlbums(Album album){
        albumRepository.save(album);
    }
}
