package com.packt.app.generateDataBase;

import com.packt.app.Application;
import com.packt.app.genre.Genre;
import com.packt.app.track.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
public class GenerateDataBaseController {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

   private GenerateDataBaseService generateDataBaseService;


    @Autowired
    public GenerateDataBaseController(GenerateDataBaseService generateDataBaseService) {
        this.generateDataBaseService = generateDataBaseService;
    }

    @PostMapping("savetrack")
    private void saveRockTracks(@RequestParam String genre) {
        List<Track> tracks = generateDataBaseService.getAllTracksByGenre(genre);
        Genre genre1 = generateDataBaseService.getGenre(genre);
        generateDataBaseService.saveData(tracks, genre1);
    }


}


