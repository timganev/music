package com.packt.app.generateDataBase;

import com.packt.app.GenerateDataBase.GenerateDataBaseService;
import com.packt.app.artist.Artist;
import com.packt.app.artist.ArtistRepository;
import com.packt.app.artist.ArtistService;
import com.packt.app.artist.ArtistServiceImpl;
import com.packt.app.genre.Genre;
import com.packt.app.track.Track;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class GenerateDataBaseServiceImplTest {

    @Test
    public void getGenre_Should_Return_Matched_Genre() {

        GenerateDataBaseService generateDataBaseService = Mockito.mock(GenerateDataBaseService.class);

        Genre genre=new Genre("Rock");

        Mockito.when(generateDataBaseService.getGenre("Rock"))
                .thenReturn(
                        genre
                );

        //Act
        Genre result = generateDataBaseService.getGenre("Rock");

        //Assert
        Assert.assertEquals("Rock", result.getName());

    }

}
