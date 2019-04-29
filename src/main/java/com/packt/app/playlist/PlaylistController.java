package com.packt.app.playlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.app.track.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class PlaylistController {

    private PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }


    public Iterable<Playlist> getPlaylists(){
        return playlistService.getPlaylists();
    }


    @PostMapping("generateone")
 public void generateByOneGenre(HttpServletRequest req, HttpServletResponse res) throws IOException {
        playlistService.generatePlaylistByOneGenre(req,res);
    }

    @PostMapping("generatemore")
 public void generateByMoreGenres(HttpServletRequest req, HttpServletResponse res) throws IOException {
        playlistService.generatePlaylistByMoreGenres(req,res);
    }

    @GetMapping("getplaylist")
 public List<Playlist> getPlaylistByTitle(@RequestParam String title) {
       return playlistService.getPlaylistByTitle(title);
    }
    @GetMapping("playlisttracks")
 public Set<Track> getPlaylistTracks(@RequestParam String title) {
       return playlistService.getPlaylistTracks(title);
    }





}
