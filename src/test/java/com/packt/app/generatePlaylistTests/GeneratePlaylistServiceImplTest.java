package com.packt.app.generatePlaylistTests;

import com.packt.app.GeneratePlaylist.GeneratePlaylistService;
import com.packt.app.GeneratePlaylist.GeneratePlaylistServiceImpl;
import com.packt.app.artist.Artist;
import com.packt.app.artist.ArtistRepository;
import com.packt.app.artist.ArtistService;
import com.packt.app.artist.ArtistServiceImpl;
import com.packt.app.genre.GenreRepository;
import com.packt.app.genre.GenreServiceImpl;
import com.packt.app.playlist.Playlist;
import com.packt.app.playlist.PlaylistCredentials;
import com.packt.app.playlist.PlaylistRepository;
import com.packt.app.playlist.PlaylistServiceImpl;
import com.packt.app.track.TrackRepository;
import com.packt.app.track.TrackServiceImpl;
import com.packt.app.user.UserRepository;
import com.packt.app.user.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class GeneratePlaylistServiceImplTest {

//
//    @Test
//    public void getCurrentDuration_Should_Return_Playlist_CurrentDuration() {
//
//        GeneratePlaylistService generatePlaylistService=Mockito.mock(GeneratePlaylistService.class);;
//        PlaylistCredentials playlistCredentials=new PlaylistCredentials("All",100);
//
//        //Act
//        double result = generatePlaylistService.getCurrentDuration(0,new Playlist(),0,
//                                                                        playlistCredentials,"All");
//
//        //Assert
//        Assert.assertTrue(result >= 0);
//
//    }

//    @Test
//    public void saveGeneratedPlaylist_Should_Save_Generated_Plylist() throws IOException {
//        GeneratePlaylistService generatePlaylistService=Mockito.mock(GeneratePlaylistService.class);
//        PlaylistRepository playlistRepository=Mockito.mock(PlaylistRepository.class);
//
//        HttpServletRequest req=Mockito.mock(HttpServletRequest.class);
//        HttpServletResponse res=Mockito.mock(HttpServletResponse.class);
//
//        generatePlaylistService.saveGeneratedPlaylistByOneGenre(req,res);
//        Playlist playlist=generatePlaylistService.generatePlaylistByMoreGenres(req,res);
//
//        //Assert
//        Mockito.verify(playlistRepository, Mockito.times(1)).save(playlist);
//
//    }
}
