package com.packt.app.GenerateDataBase;

import com.packt.app.ResponseErrorHandler.RestTemplateResponseErrorHandler;
import com.packt.app.album.AlbumService;
import com.packt.app.artist.ArtistService;
import com.packt.app.genre.Genre;
import com.packt.app.genre.GenreService;
import com.packt.app.logger.LoggerService;
import com.packt.app.playlist.PlaylistDTO;
import com.packt.app.playlist.PlaylistList;
import com.packt.app.track.Track;
import com.packt.app.track.TrackList;
import com.packt.app.track.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GenerateDataBaseController {

    private TrackService trackService;
    private AlbumService albumService;
    private ArtistService artistService;
    private GenreService genreService;
    private RestTemplate restTemplate;
    private LoggerService loggerService;

    @Autowired
    public GenerateDataBaseController(TrackService trackService, AlbumService albumService,
                                      ArtistService artistService, GenreService genreService,
                                      RestTemplateBuilder restTemplateBuilder,
                                      LoggerService loggerService ) {
        this.trackService = trackService;
        this.albumService = albumService;
        this.artistService = artistService;
        this.genreService = genreService;
        this.restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
        this.loggerService=loggerService;
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

    @GetMapping("gettracks")
    private List<Track> getAllRockTracks(String genreType) {

        Genre genre = getRockGenre(genreType);
        List<Track> alltracks = new ArrayList<>();

        for (String playlistTrack : getPlaylistTracks(genre)) {
            TrackList response = restTemplate.getForObject(playlistTrack, TrackList.class);
            alltracks.addAll(response.getTracks());

        }
        return alltracks;
    }

    @PostMapping("saverock")
    private void saveRockTracks() {
        List<Track> tracks = getAllRockTracks("Rock");
        Genre genre = getRockGenre("Rock");

        saveData(tracks, genre);
        loggerService.doStuff("Rock tracks was saved fo DB");

    }

    @PostMapping("savepop")
    private void savePopTracks() {
        List<Track> tracks = getAllRockTracks("Pop");
        Genre genre = getRockGenre("Pop");
        saveData(tracks, genre);

    }

    @PostMapping("savedance")
    private void saveDanceTracks() {
        List<Track> tracks = getAllRockTracks("Dance");
        Genre genre = getRockGenre("Dance");
        saveData(tracks, genre);

    }

    @PostMapping("saverb")
    private void saveRBTracks() {
        List<Track> tracks = getAllRockTracks("R&B");
        Genre genre = getRockGenre("R&B");
        saveData(tracks, genre);

    }

    private Genre getRockGenre(String genreType) {
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

            String message=String.format("Track with genre - %s was saved to DB", genre.getName());
            loggerService.doStuff(message);
        }
    }

}
