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

    private TrackService trackService;
    private AlbumService albumService;
    private ArtistService artistService;
    private GenreService genreService;
    private RestTemplate restTemplate;


    @Autowired
    public GenerateDataBaseController(TrackService trackService, AlbumService albumService,
                                      ArtistService artistService, GenreService genreService,
                                      RestTemplateBuilder restTemplateBuilder) {
        this.trackService = trackService;
        this.albumService = albumService;
        this.artistService = artistService;
        this.genreService = genreService;
        this.restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();

    }

    private List<String> getPlaylistTracks(Genre genre) throws NullPointerException {

        String url = "https://api.deezer.com/search/playlist?q=" + genre.getName().toLowerCase() + "&index=0&limit=70%22";
        PlaylistList response = restTemplate.getForObject(url, PlaylistList.class);

        List<PlaylistDTO> list = response.getPlaylist();
        List<String> playlistTracks = new ArrayList<>();

        for (PlaylistDTO playlistDTO : list) {
            playlistTracks.add(playlistDTO.getTrackUrl());
        }
        return playlistTracks;
    }

    private List<Track> getAllTracksByGenre(String genreType) {

        Genre genre = getGenre(genreType);
        List<Track> alltracks = new ArrayList<>();

        for (String playlistTrack : getPlaylistTracks(genre)) {
            TrackList response = restTemplate.getForObject(playlistTrack, TrackList.class);
            alltracks.addAll(response.getTracks());

        }
        return alltracks;
    }

    @PostMapping("saverock")
    private void saveRockTracks() {
        List<Track> tracks = getAllTracksByGenre("Rock");
        Genre genre = getGenre("Rock");
        saveData(tracks, genre);
        logger.warn(DOWNLOADED_ROCK_TRACKS_SAVED_MESSAGE);
    }

    @PostMapping("savepop")
    private void savePopTracks() {
        List<Track> tracks = getAllTracksByGenre("Pop");
        Genre genre = getGenre("Pop");
        saveData(tracks, genre);
        logger.warn(DOWNLOADED_POP_TRACKS_SAVED_MESSAGE);
    }

    @PostMapping("savedance")
    private void saveDanceTracks() {
        List<Track> tracks = getAllTracksByGenre("Dance");
        Genre genre = getGenre("Dance");
        saveData(tracks, genre);
        logger.warn(DOWNLOADED_DANCE_TRACKS_SAVED_MESSAGE);
    }

    @PostMapping("saverb")
    private void saveRBTracks() {
        List<Track> tracks = getAllTracksByGenre("R&B");
        Genre genre = getGenre("R&B");
        saveData(tracks, genre);
        logger.warn(DOWNLOADED_RB_TRACKS_SAVED_MESSAGE);

    }

    private Genre getGenre(String genreType) {
        Genre genre = new Genre();
        genre=genreService.findByName(genreType);
        return genre;
    }


    private void saveData(List<Track> tracks, Genre genre) {
        for (Track track : tracks) {
            artistService.saveArtist(track.getArtist());
            track.getAlbum().setArtist(track.getArtist());
            albumService.saveAlbums(track.getAlbum());
            track.setGenre(genre);
            trackService.saveTrack(track);

            String message=String.format(SAVE_TRACK_TO_DB_MESSAGE, genre.getName());
            logger.info(message);
        }
    }

}
