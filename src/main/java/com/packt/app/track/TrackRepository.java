package com.packt.app.track;




import com.packt.app.genre.Genre;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

import static com.packt.app.constants.Constants.*;

@RepositoryRestResource
public interface TrackRepository extends CrudRepository<Track, Integer> {

//    List<Beer> findByAbvBetweenOrderByAbvDesc(Integer min, Integer max);
List<Track> findAllByGenreAndDurationBetween(Integer genre, Integer min, Integer max);
List<Track> findAllByGenre_Id(Integer genre);
List<Track> findAllByDurationBetween(Integer min, Integer max);

    @Query(value = QUEERY_RANDOM_TRACK,
            nativeQuery = true)
    Track getRandomTrackFromDB();

      @Query(value = QUERRY_RANDOM_TRACK_BY_ROCK_GENRE,
            nativeQuery = true)
    Track getRandomTrackFromDbByRockGenre();

      @Query(value = QUERRY_RANDOM_TRACK_BY_DANCE_GENRE,
            nativeQuery = true)
    Track getRandomTrackFromDbByDanceGenre();

  @Query(value = QUERRY_RANDOM_TRACK_BY_RB_GENRE,
            nativeQuery = true)
    Track getRandomTrackFromDbByRBGenre();

  @Query(value = QUERRY_RANDOM_TRACK_BY_POP_GENRE,
            nativeQuery = true)
    Track getRandomTrackFromDbByPopGenre();





}
