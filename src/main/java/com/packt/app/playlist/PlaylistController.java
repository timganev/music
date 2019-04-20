package com.packt.app.playlist;

import com.packt.app.track.Track;
import com.packt.app.track.TrackList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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


}
