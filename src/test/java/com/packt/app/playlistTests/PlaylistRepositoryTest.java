package com.packt.app.playlistTests;

import com.packt.app.album.Album;
import com.packt.app.album.AlbumRepository;
import com.packt.app.artist.Artist;
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
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlaylistRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Test
    public void save_Playlist() {
        User user=new User("name","123456","User");
        entityManager.persistAndFlush(user);
        Playlist playlist=new Playlist("title",user,152,0);
        entityManager.persistAndFlush(playlist);

        Assert.assertEquals("title", playlist.getTitle());
    }


    @Test
    public void deletePlaylist() {
        User userId=new User("name","1223456","User");
        entityManager.persistAndFlush(userId);
        Playlist playlist=new Playlist("title",userId,152,0);
        entityManager.persistAndFlush(playlist);

        List<Playlist> emptyList=new ArrayList<>();

        playlistRepository.delete(playlist);
        Assert.assertEquals(emptyList, playlistRepository.findAll());
    }

}