package com.packt.app.artistTests;

import com.packt.app.album.Album;
import com.packt.app.album.AlbumRepository;
import com.packt.app.album.AlbumService;
import com.packt.app.album.AlbumServiceImpl;
import com.packt.app.artist.Artist;
import com.packt.app.artist.ArtistRepository;
import com.packt.app.artist.ArtistService;
import com.packt.app.artist.ArtistServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
public class ArtistServiceImplTest {

    @Test
    public void getAlbums_Should_Return_All_Existing_Albums() {

        ArtistRepository artistRepository = Mockito.mock(ArtistRepository.class);
        ArtistService artistService=new ArtistServiceImpl(artistRepository);

        Artist artist=new Artist("name","artist_track_url");
        Artist artist1=new Artist("name1","artist_track_url");

        List<Artist> list = new ArrayList<>();
        list.add(artist);
        list.add(artist1);

        Mockito.when(artistService.getArtists())
                .thenReturn(
                        list
                );

        //Act

        List<Artist> result = (List<Artist>) artistService.getArtists();

        //Assert
        Assert.assertEquals(2, result.size());

    }

    @Test
    public void saveAlbum_Should_Call_AlbumRepository_Save(){
        ArtistRepository artistRepository = Mockito.mock(ArtistRepository.class);
        ArtistService artistService=new ArtistServiceImpl(artistRepository);

        Artist artist=new Artist("name","artist_track_url");

        artistService.saveArtist(artist);
        //Assert
        Mockito.verify(artistRepository, Mockito.times(1)).save(artist);

    }
}
