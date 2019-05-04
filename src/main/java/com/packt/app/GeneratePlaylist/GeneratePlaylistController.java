package com.packt.app.GeneratePlaylist;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class GeneratePlaylistController {
    private GeneratePlaylistService generatePlaylistService;

    @Autowired
    public GeneratePlaylistController(GeneratePlaylistService generatePlaylistService) {
        this.generatePlaylistService = generatePlaylistService;
    }


    @PostMapping("generateone")
    public void generateByOneGenre(HttpServletRequest req, HttpServletResponse res) throws IOException {
        generatePlaylistService.saveGeneratedPlaylistByOneGenre(req, res);
    }

    @PostMapping("generatemore")
    public void generateByMoreGenre(HttpServletRequest req, HttpServletResponse res) throws IOException {
        generatePlaylistService.saveGeneratedPlaylistByMoreGenre(req, res);
    }

    @PostMapping("generate")
    public void generatePlaylist(HttpServletRequest req, HttpServletResponse res) throws IOException {
        generatePlaylistService.saveGeneratedPlaylist(req, res);
    }






}
