package com.packt.app.artistTests;

import com.packt.app.album.Album;
import com.packt.app.album.AlbumRepository;
import com.packt.app.artist.Artist;
import com.packt.app.artist.ArtistRepository;
import com.packt.app.playlist.Playlist;
import com.packt.app.user.User;
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
public class ArtistRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    public void save_Album() {
        Artist artist=new Artist("name","artist_track_url");
        entityManager.persistAndFlush(artist);

        Assert.assertEquals("name", artist.getName());
    }

    @Test
    public void deleteAlbum() {
        Artist artist=new Artist("name","artist_track_url");
        entityManager.persistAndFlush(artist);

        List<Artist> emptyList=new ArrayList<>();

        artistRepository.delete(artist);
        Assert.assertEquals(emptyList, artistRepository.findAll());
    }

}