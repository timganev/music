package com.packt.app.trackTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.app.album.Album;
import com.packt.app.album.AlbumController;
import com.packt.app.album.AlbumService;
import com.packt.app.artist.Artist;
import com.packt.app.genre.Genre;
import com.packt.app.genre.GenreService;
import com.packt.app.playlist.*;
import com.packt.app.track.Track;
import com.packt.app.track.TrackController;
import com.packt.app.track.TrackRepository;
import com.packt.app.track.TrackService;
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
@WebMvcTest(TrackController.class)
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
public class TrackControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TrackService service;

    @MockBean
    private GenreService genreService;

    @Test
    public void givenPlaylists_whenGetPlaylist_thenReturnJsonArray()
            throws Exception {

        TrackRepository trackRepository = Mockito.mock(TrackRepository.class);

        Album album = new Album();
        Artist artist = new Artist();
        Genre genre = new Genre();
        Track track = new Track("title", "link", 100, 5, "preview_url", genre, album, artist);
        List<Track> allTracks = Arrays.asList(track);

        given(service.getTracks()).willReturn(allTracks);

        mvc.perform(get("/tracks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(track)))
                .andExpect(jsonPath("$[0].title").value("title"));
    }

}