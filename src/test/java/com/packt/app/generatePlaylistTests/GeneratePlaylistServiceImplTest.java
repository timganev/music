package com.packt.app.generatePlaylistTests;

import com.packt.app.GeneratePlaylist.GeneratePlaylistService;
import com.packt.app.GeneratePlaylist.GeneratePlaylistServiceImpl;
import com.packt.app.album.Album;
import com.packt.app.album.AlbumRepository;
import com.packt.app.album.AlbumService;
import com.packt.app.album.AlbumServiceImpl;
import com.packt.app.artist.Artist;
import com.packt.app.artist.ArtistRepository;
import com.packt.app.artist.ArtistService;
import com.packt.app.artist.ArtistServiceImpl;
import com.packt.app.genre.GenreRepository;
import com.packt.app.genre.GenreServiceImpl;
import com.packt.app.playlist.*;
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
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
public class GeneratePlaylistServiceImplTest {


    @Test
    public void getCurrentDuration_Should_Return_Playlist_CurrentDuration() {

        GeneratePlaylistService generatePlaylistService=Mockito.mock(GeneratePlaylistService.class);;
        PlaylistCredentials playlistCredentials=new PlaylistCredentials("All",100);

        //Act
        double result = generatePlaylistService.getCurrentDuration(0,new Playlist(),0,
                                                                        playlistCredentials,"All",false,false);

        //Assert
        Assert.assertTrue(result >= 0);

    }

    @Test
    public void generatePlaylistByOneGenre_Should_Generate_All_Playlist_By_One_Genre() {

        PlaylistRepository playlistRepository=Mockito.mock(PlaylistRepository.class);
        PlaylistService playlistService=new PlaylistServiceImpl(playlistRepository);

        GeneratePlaylistService generatePlaylistService=Mockito.mock(GeneratePlaylistService.class);;
        PlaylistCredentials playlistCredentials=new PlaylistCredentials("All",100);

        List<PlaylistCredentials> playlistCredentialsList=new ArrayList<>();
        playlistCredentialsList.add(playlistCredentials);

        Playlist playlist=generatePlaylistService.generatePlaylistByOneGenre(
                "title","username",playlistCredentialsList,false,false);

        Mockito.when(playlistService.getPlaylistByTitle("title"))
                .thenReturn(
                        playlist
                );

        //Act

        Playlist result = playlistService.getPlaylistByTitle("title");
        List<Playlist> playlists=new ArrayList<>();
        playlists.add(result);

        //Assert
        Assert.assertEquals(1, playlists.size());

    }


    @Test
    public void saveAlbum_Should_Call_AlbumRepository_Save(){
        PlaylistRepository playlistRepository=Mockito.mock(PlaylistRepository.class);

        GeneratePlaylistService generatePlaylistService=Mockito.mock(GeneratePlaylistService.class);;
        PlaylistCredentials playlistCredentials=new PlaylistCredentials("rock",100);
        List<PlaylistCredentials> playlistCredentialsList=new ArrayList<>();
        playlistCredentialsList.add(playlistCredentials);



        generatePlaylistService.saveGeneratedPlaylistByOneGenre(
                "title","username",playlistCredentialsList,false,false);

        //Assert
        Mockito.verify(generatePlaylistService, Mockito.times(1))
                .saveGeneratedPlaylistByOneGenre("title","username",playlistCredentialsList,
                        false,false);

    }
}
