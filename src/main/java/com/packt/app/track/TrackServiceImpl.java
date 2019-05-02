package com.packt.app.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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

    @Override
    public List<Track> findAllByDurationBetween(Integer min, Integer max) {
        return trackRepository.findAllByDurationBetween(min,max);
    }

    @Override
    public Track getRandomTrackFromDB() {
        return trackRepository.getRandomTrackFromDB();
    }


}
