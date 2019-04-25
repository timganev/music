package com.packt.app.track;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TrackRepository extends CrudRepository<Track, Long> {

//    List<Beer> findByAbvBetweenOrderByAbvDesc(Integer min, Integer max);
List<Track> findAllByGenreAndDurationBetween(Integer genre, Integer min, Integer max);
List<Track> findAllByGenre_Id(Integer genre);
List<Track> findAllByDurationBetween(Integer min, Integer max);

    @Query(value = "SELECT * FROM track WHERE duration BETWEEN 60 and 300 ORDER BY RAND() limit 1 ",
            nativeQuery = true)
    Track getRandomTrackFromDB();


}
