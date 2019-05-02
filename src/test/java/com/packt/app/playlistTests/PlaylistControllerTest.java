package com.packt.app.playlistTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.app.album.Album;
import com.packt.app.album.AlbumController;
import com.packt.app.album.AlbumService;
import com.packt.app.artist.Artist;
import com.packt.app.playlist.*;
import com.packt.app.track.Track;
import com.packt.app.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(PlaylistController.class)
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
public class PlaylistControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlaylistService service;

    @Test
    public void givenPlaylists_whenGetPlaylist_thenReturnJsonArray()
            throws Exception {

        PlaylistRepository playlistRepository = Mockito.mock(PlaylistRepository.class);

        Playlist playlist = new Playlist("title", new User(), 152);
        List<Playlist> allPlaylists = Arrays.asList(playlist);

        given(service.getPlaylists()).willReturn(allPlaylists);

        mvc.perform(get("/allpalylists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(playlist)))
                .andExpect(jsonPath("$[0].title").value("title"));
    }

//    @Test
//    public void givenPlaylist_whenGetPlaylisetByTitle_thenReturnJsonArray()
//            throws Exception {
//
//
//        PlaylistRepository playlistRepository = Mockito.mock(PlaylistRepository.class);
//        PlaylistService playlistService = new PlaylistServiceImpl(playlistRepository);
//
//        Playlist playlist = new Playlist("title", new User(), 152);
//
//
//        given(service.getPlaylistByTitle("title")).willReturn(playlist);
//
//        mvc.perform(get("/allpalylists")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(playlist)))
//                .andExpect(jsonPath("$[0].title").value("title"));
//
//    }
//


//    @Test
//    public void givenPlaylistTracks_whenGetPlaylistTracks_thenReturnJsonArray()
//            throws Exception {
//
//
//        PlaylistRepository playlistRepository = Mockito.mock(PlaylistRepository.class);
//        PlaylistService playlistService = new PlaylistServiceImpl(playlistRepository);
//
//        Playlist playlist = new Playlist("title", new User(), 152);
//        Set<Track> allPlaylistsTracks =playlist.getPlaylistTracks();
//
//        given(service.getPlaylistTracks("title")).willReturn(allPlaylistsTracks);
//
//        mvc.perform(get("/playlisttracks")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(playlist)))
//                .andExpect(jsonPath("$").value(allPlaylistsTracks));
//
//    }
}
