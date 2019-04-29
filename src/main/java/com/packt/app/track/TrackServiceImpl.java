package com.packt.app.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl implements TrackService{

    private TrackRepository trackRepository;
    public String GENRE_VALUE="";
    private String QUERRY_BY_GENRE="SELECT * FROM track WHERE genre=" + getGENRE_VALUE()
            + " BETWEEN 60 and 300 ORDER BY RAND() limit 1";


    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    private String getGENRE_VALUE() {
        return GENRE_VALUE;
    }

    public void setGENRE_VALUE(String GENRE_VALUE) {
        this.GENRE_VALUE = GENRE_VALUE;
    }

    public Iterable<Track> getTracks(){
        return trackRepository.findAll();
    }

    public void saveTrack(Track track){
        trackRepository.save(track);
    }

    @Override
    public List<Track> findAllByGenre_IdAndDurationBetween(Integer genre, Integer min, Integer max) {
        return trackRepository.findAllByGenreAndDurationBetween( genre,  min,  max);
    }

    @Override
    public List<Track> findAllByGenre_Id(Integer genre) {
        return trackRepository.findAllByGenre_Id(genre);
    }

    @Override
    public List<Track> findAllByDurationBetween(Integer min, Integer max) {
        return trackRepository.findAllByDurationBetween(min,max);
    }

    @Override
    public Track getRandomTrackFromDB() {
        return trackRepository.getRandomTrackFromDB();
    }

    @Override
    public Track getRandomTrackFromDbByRockGenre() {
        return trackRepository.getRandomTrackFromDbByRockGenre();
    }

}
