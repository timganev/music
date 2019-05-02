package com.packt.app.albumTests;

import com.packt.app.album.Album;
import com.packt.app.album.AlbumRepository;
import com.packt.app.album.AlbumService;
import com.packt.app.album.AlbumServiceImpl;
import com.packt.app.artist.Artist;
import com.packt.app.playlist.Playlist;
import com.packt.app.playlist.PlaylistRepository;
import com.packt.app.playlist.PlaylistService;
import com.packt.app.playlist.PlaylistServiceImpl;
import com.packt.app.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
public class AlbumServiceImplTest {

    @Test
    public void getAlbums_Should_Return_All_Existing_Albums() {

        AlbumRepository albumRepository = Mockito.mock(AlbumRepository.class);
        AlbumService albumService=new AlbumServiceImpl(albumRepository);

        Artist artist=new Artist("name","artist_track_url");

        Album album = new Album("title","album_track",artist,new HashSet<>());
        Album album1 = new Album("title1","album_track1",artist,new HashSet<>());


        List<Album> list = new ArrayList<>();
        list.add(album);
        list.add(album1);

        Mockito.when(albumService.getAlbums())
                .thenReturn(
                        list
                );

        //Act

        List<Album> result = (List<Album>) albumService.getAlbums();

        //Assert
        Assert.assertEquals(2, result.size());

    }

    @Test
    public void saveAlbum_Should_Call_AlbumRepository_Save(){
        AlbumRepository albumRepository = Mockito.mock(AlbumRepository.class);
        AlbumService albumService=new AlbumServiceImpl(albumRepository);

        Artist artist=new Artist("name","artist_track_url");

        Album album = new Album("title","album_track",artist,new HashSet<>());


        albumService.saveAlbums(album);
        //Assert
        Mockito.verify(albumRepository, Mockito.times(1)).save(album);

    }
}
