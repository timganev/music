package com.packt.app.albumTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.app.album.Album;
import com.packt.app.album.AlbumController;
import com.packt.app.album.AlbumService;
import com.packt.app.artist.Artist;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AlbumService service;

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void givenAlbums_whenGetAlbums_thenReturnJsonArray()
            throws Exception {

        Artist artist=new Artist("name","artist_track_url");

        Album album = new Album("title","album_track",artist,new HashSet<>());

        List<Album> allAlbums = Arrays.asList(album);

        given(service.getAlbums()).willReturn(allAlbums);

        mvc.perform(get("/albums")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(album)))
                .andExpect(jsonPath("$[0].title").value("title"));
    }

}