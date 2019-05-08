package com.packt.app.trackTests;

import com.packt.app.album.Album;
import com.packt.app.artist.Artist;
import com.packt.app.genre.Genre;
import com.packt.app.playlist.Playlist;
import com.packt.app.playlist.PlaylistRepository;
import com.packt.app.playlist.PlaylistService;
import com.packt.app.playlist.PlaylistServiceImpl;
import com.packt.app.track.Track;
import com.packt.app.track.TrackRepository;
import com.packt.app.track.TrackService;
import com.packt.app.track.TrackServiceImpl;
import com.packt.app.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
public class TrackServiceImplTest {

    @Test
    public void getPlaylists_Should_Return_All_Existing_Albums() {

        TrackRepository trackRepository = Mockito.mock(TrackRepository.class);
        TrackService trackService = new TrackServiceImpl(trackRepository);

        Album album = new Album();
        Artist artist = new Artist();
        Genre genre = new Genre();
        Track track = new Track("title", "link", 100, 5, "preview_url", genre, album, artist);
        Track track1 = new Track("title1", "link", 100, 5, "preview_url", genre, album, artist);



        List<Track> list = new ArrayList<>();
        list.add(track);
        list.add(track1);

        Mockito.when(trackService.getTracks())
                .thenReturn(
                        list
                );

        //Act

        List<Track> result = (List<Track>) trackService.getTracks();

        //Assert
        Assert.assertEquals(2, result.size());

    }



    @Test
    public void saveTrack_Should_Call_TrackRepository_Save(){

        TrackRepository trackRepository = Mockito.mock(TrackRepository.class);
        TrackService trackService = new TrackServiceImpl(trackRepository);

        Album album = new Album();
        Artist artist = new Artist();
        Genre genre = new Genre();
        Track track = new Track("title", "link", 100, 5, "preview_url", genre, album, artist);

        trackService.saveTrack(track);

        //Assert
        Mockito.verify(trackRepository, Mockito.times(1)).save(track);

    }

}
