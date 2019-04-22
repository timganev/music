package com.packt.app.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl implements TrackService{

    private TrackRepository trackRepository;

    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public Iterable<Track> getTracks(){
        return trackRepository.findAll();
    }

    public void saveTrack(Track track){
        trackRepository.save(track);
    }

//    public List<Track> getT
}
