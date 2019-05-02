package com.packt.app.genreTests;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.packt.app.genre.Genre;
import com.packt.app.genre.GenreController;
import com.packt.app.genre.GenreService;
import com.packt.app.genre.GenreServiceImpl;
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

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@RunWith(SpringRunner.class)
@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreServiceImpl service;

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void givenAlbums_whenGetAlbums_thenReturnJsonArray()
            throws Exception {

        Genre genre=new Genre("name");

        List<Genre> allAlbums = Arrays.asList(genre);

        given(service.getGenres()).willReturn(allAlbums);

        mvc.perform(get("/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(genre)))
                .andExpect(jsonPath("$[0].name").value("name"));
    }



}