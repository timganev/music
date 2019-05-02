package com.packt.app.trackTests;

import com.packt.app.album.Album;
import com.packt.app.artist.Artist;
import com.packt.app.genre.Genre;
import com.packt.app.playlist.Playlist;
import com.packt.app.playlist.PlaylistRepository;
import com.packt.app.track.Track;
import com.packt.app.track.TrackRepository;
import com.packt.app.track.TrackService;
import com.packt.app.track.TrackServiceImpl;
import com.packt.app.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TrackRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TrackRepository trackRepository;

    @Test
    public void save_Album() {
        Album album=new Album();
        entityManager.persistAndFlush(album);

        Artist artist=new Artist();
        entityManager.persistAndFlush(artist);

        Genre genre=new Genre();
        entityManager.persistAndFlush(artist);

        Track track=new Track("title","link",100,5,"preview_url",genre,album,artist);
        entityManager.persistAndFlush(track);

        Assert.assertEquals("title", track.getTitle());
    }


    @Test
    public void deleteTrack() {

        Album album=new Album();
        entityManager.persistAndFlush(album);

        Artist artist=new Artist();
        entityManager.persistAndFlush(artist);

        Genre genre=new Genre();
        entityManager.persistAndFlush(artist);

        Track track=new Track("title","link",100,5,"preview_url",genre,album,artist);
        entityManager.persistAndFlush(track);

        List<Track> emptyList=new ArrayList<>();

        trackRepository.delete(track);
        Assert.assertEquals(emptyList, trackRepository.findAll());
    }

    @Test
    public void getRandomTrackFromDB_Should_Return_RandomTrack(){
        TrackRepository trackRepository = Mockito.mock(TrackRepository.class);
        TrackService trackService = new TrackServiceImpl(trackRepository);

        Album album = new Album();
        Artist artist = new Artist();
        Genre genre = new Genre();
        Track track = new Track("title", "link", 100, 5, "preview_url", genre, album, artist);


        trackService.getRandomTrackFromDB();

        //Assert
        Mockito.verify(trackRepository, Mockito.times(1)).getRandomTrackFromDB();
    }
}
