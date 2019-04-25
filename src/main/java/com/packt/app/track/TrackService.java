package com.packt.app.track;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrackService {
    Iterable<Track> getTracks();
    void saveTrack(Track track);
    List<Track> findAllByGenre_IdAndDurationBetween(Integer genre, Integer min, Integer max);
    List<Track> findAllByGenre_Id(Integer genre);
    List<Track> findAllByDurationBetween(Integer min, Integer max);

    @Query(value = "SELECT * FROM track ORDER BY RAND() limit 1",
            nativeQuery = true)
     Track getRandomTrackFromDB();

}
