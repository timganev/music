package com.packt.app.albumTests;

import com.packt.app.album.Album;
import com.packt.app.album.AlbumRepository;
import com.packt.app.artist.Artist;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AlbumRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AlbumRepository albumRepository;

    @Test
    public void save_Album() {
        Artist artist=new Artist("name","artist_track_url");
        entityManager.persistAndFlush(artist);

        Album album = new Album("title","album_track",artist,new HashSet<>());
        entityManager.persistAndFlush(album);

        Assert.assertEquals("title", album.getTitle());
    }


    @Test
    public void deleteAlbum() {
        Artist artist=new Artist("name","artist_track_url");
        entityManager.persistAndFlush(artist);

        Album album = new Album("title","album_track",artist,new HashSet<>());
        entityManager.persistAndFlush(album);

        List<Album> emptyList=new ArrayList<>();

        albumRepository.delete(album);
        Assert.assertEquals(emptyList, albumRepository.findAll());
    }
}
