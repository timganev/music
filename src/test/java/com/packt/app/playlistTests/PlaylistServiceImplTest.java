package com.packt.app.playlistTests;


import com.packt.app.playlist.Playlist;
import com.packt.app.playlist.PlaylistRepository;
import com.packt.app.playlist.PlaylistService;
import com.packt.app.playlist.PlaylistServiceImpl;
import com.packt.app.track.Track;

import com.packt.app.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
public class PlaylistServiceImplTest {

    @Test
    public void findByTitle_Should_Return_Playlist_When_Match_Exist() {

        PlaylistRepository playlistRepository = Mockito.mock(PlaylistRepository.class);
        PlaylistService playlistService=new PlaylistServiceImpl(playlistRepository);

        Playlist playlist=new Playlist("title",new User(),152,0);

        Mockito.when(playlistService.getPlaylistByTitle("title"))
                .thenReturn(
                        playlist
                );

        //Act

        Playlist result = playlistService.getPlaylistByTitle("title");

        //Assert
        Assert.assertEquals("title", result.getTitle());

    }

    @Test
    public void getPlaylists_Should_Return_All_Existing_Albums() {

        PlaylistRepository playlistRepository = Mockito.mock(PlaylistRepository.class);
        PlaylistService playlistService=new PlaylistServiceImpl(playlistRepository);

        Playlist playlist=new Playlist("title",new User(),152,0);
        Playlist playlist1=new Playlist("title1",new User(),152,0);



        List<Playlist> list = new ArrayList<>();
        list.add(playlist);
        list.add(playlist1);

        Mockito.when(playlistService.getPlaylists())
                .thenReturn(
                        list
                );

        //Act

        List<Playlist> result = (List<Playlist>) playlistService.getPlaylists();

        //Assert
        Assert.assertEquals(2, result.size());

    }

    @Test
    public void savePlaylist_Should_Call_PlaylistRepository_Save(){
        PlaylistRepository playlistRepository = Mockito.mock(PlaylistRepository.class);
        PlaylistService playlistService=new PlaylistServiceImpl(playlistRepository);

        Playlist playlist=new Playlist("title",new User(),152,0);

        playlistService.save(playlist);

        //Assert
        Mockito.verify(playlistRepository, Mockito.times(1)).save(playlist);

    }

    @Test(expected = NullPointerException.class)
    public void getPlaylistsTracks_Throw_When_Have_No_Tracks() {

        PlaylistRepository playlistRepository = Mockito.mock(PlaylistRepository.class);
        PlaylistService playlistService=new PlaylistServiceImpl(playlistRepository);

        User user=new User("name","123456","User");

        Set<Track> list = new HashSet<>();

        //Act
        Set<Track> result = playlistService.getPlaylistTracks("title");

    }
}
