package com.packt.app.genreTests;

import com.packt.app.genre.Genre;
import com.packt.app.genre.GenreRepository;
import com.packt.app.playlist.Playlist;
import com.packt.app.playlist.PlaylistRepository;
import com.packt.app.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GenreRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void save_Album() {
        Genre genre=new Genre("name");
        entityManager.persistAndFlush(genre);

        Assert.assertEquals("name", genre.getName());
    }


    @Test
    public void deleteAlbum() {
        Genre genre=new Genre("name");
        entityManager.persistAndFlush(genre);

        List<Genre> emptyList=new ArrayList<>();

        genreRepository.delete(genre);
        Assert.assertEquals(emptyList, genreRepository.findAll());
    }

}