package com.packt.app.artistTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.app.album.Album;
import com.packt.app.album.AlbumController;
import com.packt.app.album.AlbumService;
import com.packt.app.artist.Artist;
import com.packt.app.artist.ArtistController;
import com.packt.app.artist.ArtistService;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArtistService service;

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void givenAlbums_whenGetAlbums_thenReturnJsonArray()
            throws Exception {

        Artist artist=new Artist("name","artist_track_url");

        List<Artist> allAlbums = Arrays.asList(artist);

        given(service.getArtists()).willReturn(allAlbums);

        mvc.perform(get("/artists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(artist)))
                .andExpect(jsonPath("$[0].name").value("name"));
    }

}