package com.packt.app.generatePlaylist;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@RestController
public class GeneratePlaylistController {
    private GeneratePlaylistService generatePlaylistService;

    @Autowired
    public GeneratePlaylistController(GeneratePlaylistService generatePlaylistService) {
        this.generatePlaylistService = generatePlaylistService;
    }


    @PostMapping("generate")
    public void generatePlaylist(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {
        generatePlaylistService.generatePlaylist(req,res);
    }


}
