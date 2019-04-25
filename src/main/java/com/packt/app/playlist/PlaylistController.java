package com.packt.app.playlist;

import com.packt.app.track.Track;
import com.packt.app.track.TrackList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

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


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/generate", params = {"title", "username", "duration"}, method = GET)
    @ResponseBody
    public void generatePlaylist(String title, String username, double duration){
       playlistService.generatePlaylist(title,username,duration);
    }


}
