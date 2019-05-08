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



      List<Track> getAllByGenreOrderByRankDesc(Genre genre);

//        @Query(value = "select * from track group by artist",nativeQuery = true)
//        List<Track> findAllGroupByArtist();
//
//    @Query(value = "select * from track group by title",nativeQuery = true)
//        List<Track> findAllGroupByTitle();

    @Query(value = "select * from track where duration<400 and genre=:genreId group by title",nativeQuery = true)
    List<Track> findAllByGenreGroupByTitle( @Param("genreId") int genreId);

    @Query(value = "select * from track where duration<400 and genre=:genreId group by artist",nativeQuery = true)
    List<Track> findAllByGenreGroupByArtist(@Param("genreId") int genreId);

//    @Query(value = "select * from track where duration<400 group by artist order by rank desc",nativeQuery = true)
//    List<Track> findAllTracksGroupByArtistOrderByRankDesc();

    @Query(value = "select * from track where duration<400 and genre=:genreId group by artist order by rank desc",nativeQuery = true)
    List<Track> findAllTracksByGenreGroupByArtistOrderByRankDesc(@Param("genreId") int genreId);

   @Query(value = "select * from track where duration<400 and genre=:genreId order by rank desc",nativeQuery = true)
    List<Track> findAllTracksByGenreOrderByRankDesc(@Param("genreId") int genreId);

//   @Query(value = "select * from track order by rank desc",nativeQuery = true)
//    List<Track> findAllTracksOrderByRankDesc();




}
