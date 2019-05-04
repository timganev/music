package com.packt.app.GenerateDataBase;

import com.packt.app.Application;
import com.packt.app.ResponseErrorHandler.RestTemplateResponseErrorHandler;
import com.packt.app.album.AlbumService;
import com.packt.app.artist.ArtistService;
import com.packt.app.genre.Genre;
import com.packt.app.genre.GenreService;
import com.packt.app.playlist.PlaylistDTO;
import com.packt.app.playlist.PlaylistList;
import com.packt.app.track.Track;
import com.packt.app.track.TrackList;
import com.packt.app.track.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.packt.app.constants.Constants.*;

@RestController
public class GenerateDataBaseController {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

   private GenerateDataBaseService generateDataBaseService;


    @Autowired
    public GenerateDataBaseController(GenerateDataBaseService generateDataBaseService) {
        this.generateDataBaseService = generateDataBaseService;
    }

    @PostMapping("savetrack")
    private void saveRockTracks(String genre) {
        List<Track> tracks = generateDataBaseService.getAllTracksByGenre(genre);
        Genre genre1 = generateDataBaseService.getGenre(genre);
        generateDataBaseService.saveData(tracks, genre1);
        String st=String.format(DOWNLOADED_TRACKS_WITH_GENRES_SAVED_MESSAGE,genre);
        logger.warn(st);
    }


}


