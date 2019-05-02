package com.packt.app.genreTests;

import com.packt.app.album.Album;
import com.packt.app.album.AlbumRepository;
import com.packt.app.album.AlbumService;
import com.packt.app.album.AlbumServiceImpl;
import com.packt.app.artist.Artist;
import com.packt.app.genre.Genre;
import com.packt.app.genre.GenreRepository;
import com.packt.app.genre.GenreService;
import com.packt.app.genre.GenreServiceImpl;
import com.packt.app.playlist.Playlist;
import com.packt.app.playlist.PlaylistRepository;
import com.packt.app.playlist.PlaylistService;
import com.packt.app.playlist.PlaylistServiceImpl;
import com.packt.app.user.User;
import com.sun.deploy.xml.GeneralEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@RunWith(SpringRunner.class)
public class GenreServiceImplTest {

    @Test
    public void getAlbums_Should_Return_All_Existing_Albums() {

        GenreRepository genreRepository = Mockito.mock(GenreRepository.class);
        GenreService genreService=new GenreServiceImpl(genreRepository);

        Genre genre=new Genre("name");
        Genre genre1=new Genre("name1");


        List<Genre> list = new ArrayList<>();
        list.add(genre);
        list.add(genre1);

        Mockito.when(genreService.getGenres())
                .thenReturn(
                        list
                );

        //Act
        List<Genre> result = (List<Genre>) genreService.getGenres();

        //Assert
        Assert.assertEquals(2, result.size());

    }

    @Test
    public void downloadGenresFromAPI_Should_Download_Genres_From_External_API(){
        GenreRepository genreRepository = Mockito.mock(GenreRepository.class);
        GenreService genreService=new GenreServiceImpl(genreRepository);

        List<Genre> result=genreService.downloadGenresFromAPI();

        //Assert
        Assert.assertEquals(22, result.size());

    }

    @Test
    public void saveGenres_Should_Downloaded_Genres(){
        GenreRepository genreRepository = Mockito.mock(GenreRepository.class);
        GenreService genreService=new GenreServiceImpl(genreRepository);

        List<Genre> result=new ArrayList<>();

        genreService.saveGenres();
        //Assert
        Assert.assertEquals(result, genreRepository.findAll());

    }

    @Test
    public void findByNme_Should_Return_Genre_When_Match_Exist() {

        GenreRepository genreRepository = Mockito.mock(GenreRepository.class);
        GenreService genreService=new GenreServiceImpl(genreRepository);

        Genre genre=new Genre("name");

        Mockito.when(genreService.findByName("name"))
                .thenReturn(
                        genre
                );

        //Act

        Genre result = genreService.findByName("name");

        //Assert
        Assert.assertEquals("name", result.getName());

    }


}
