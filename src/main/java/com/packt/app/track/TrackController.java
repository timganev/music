package com.packt.app.track;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class TrackController {

    private TrackService trackService;

    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping("/tracks")
    public Iterable<Track> getTracks(){
        return trackService.getTracks();
    }


//    @GetMapping("/save")
//    public void getAllTrackUsingWrapperClass ()
//    {
//        RestTemplate restTemplate = new RestTemplate();
//
//        TrackList response =
//                restTemplate.getForObject(
//                        "https://api.deezer.com/album/302127/tracks",
//                        TrackList.class);
//
//        List<Track> tracks = response.getAlbumTracks();
//
//        for (Track track : tracks) {
//            trackService.saveTrack(track);
//        }


}
