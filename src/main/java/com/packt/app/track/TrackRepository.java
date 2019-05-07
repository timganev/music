package com.packt.app.track;

import com.packt.app.genre.Genre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import java.util.List;

import static com.packt.app.constants.Constants.*;

@RepositoryRestResource
public interface TrackRepository extends CrudRepository<Track, Long> {


List<Track> findAllByGenreAndDurationBetween(Integer genre, Integer min, Integer max);
List<Track> findAllByGenre_Id(Integer genre);
List<Track> findAllByDurationBetween(Integer min, Integer max);

    @Query(value = QUEERY_RANDOM_TRACK,
            nativeQuery = true)
    Track getRandomTrackFromDB();

      @Query(value = QUERRY_RANDOM_TRACK_BY_GENRE,
            nativeQuery = true)
    Track getRandomTrackFromDbByGenre(@Param("id") int id);

      List<Track> getAllByGenreOrderByRankDesc(Genre genre);





}
