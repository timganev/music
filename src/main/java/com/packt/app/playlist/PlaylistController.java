package com.packt.app.playlist;

import com.packt.app.track.Track;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;


@RestController
public class PlaylistController {

    private PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }


    @GetMapping("/allpalylists")
    public ResponseEntity<Iterable<Playlist>> getPlaylists() {
        if (playlistService.getPlaylists().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Playlists not found");
        }
        return new ResponseEntity<>(playlistService.getPlaylists(), HttpStatus.OK);
    }


    @GetMapping("getplaylist")
    public ResponseEntity<Playlist> getPlaylistByTitle(@RequestParam String title) {
        if (playlistService.getPlaylistByTitle(title)==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Playlist not found");
        }
        return new ResponseEntity<>(playlistService.getPlaylistByTitle(title), HttpStatus.OK);
    }

    @GetMapping("playlisttracks")
    public ResponseEntity<Set<Track>> getPlaylistTracks(@RequestParam String title) {
        if (playlistService.getPlaylistTracks(title).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No tracks found");
        }
        return new ResponseEntity<>( playlistService.getPlaylistTracks(title), HttpStatus.OK);
    }


}
