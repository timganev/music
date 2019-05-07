package com.packt.app.generateDataBaseTests;

import com.packt.app.generateDataBase.GenerateDataBaseService;
import com.packt.app.genre.Genre;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

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
