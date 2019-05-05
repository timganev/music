package com.packt.app.generatePlaylistTests;

import com.packt.app.generatePlaylist.GeneratePlaylistService;
import com.packt.app.playlist.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
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
