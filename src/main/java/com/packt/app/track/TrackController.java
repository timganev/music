package com.packt.app.track;

import com.packt.app.genre.Genre;
import com.packt.app.genre.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static org.springframework.web.bind.annotation.RequestMethod.GET;


@RestController
public class TrackController {

    private TrackService trackService;
    private GenreService genreService;


    @Autowired
    public TrackController(TrackService trackService,GenreService genreService) {
        this.trackService = trackService;
        this.genreService = genreService;
    }

    @GetMapping("/tracks")
    public Iterable<Track> getTracks(){
        return trackService.getTracks();
    }

    //    http://localhost:8080//findbyduration?min=1&max=200
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/findbyduration", params = {"min", "max"}, method = GET)
    @ResponseBody
    public List<Track> findAllByDurationBetween(Integer min, Integer max) {
        return trackService.findAllByDurationBetween( min,max);

    }


    //    http://localhost:8080//filter?genre=1&min=1&max=200
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//    @RequestMapping(value = "/filter", params = {"genre", "min", "max"}, method = GET)
//    @ResponseBody
//    public List<Track> findAllByGenreAndDurationBetween(Integer genre, Integer min, Integer max) {
//        return trackService.findAllByGenre_IdAndDurationBetween( genre,  min,  max);
//    }
//
//
//    //    http://localhost:8080/findbygenre?genre=152
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//    @RequestMapping(value = "/findbygenre", params = {"genre"}, method = GET)
//    @ResponseBody
//    public List<Track> findAllByGenre(Integer genre) {
//        return trackService.findAllByGenre_Id(genre);
//    }


//    @GetMapping("/randomrock")
//    public Track getRandomTrack(@RequestParam String genre){
//
//        Genre genre1=genreService.findByName(genre);
//       Track track=trackService.getRandomTrackFromDbByGenre(genre1.getId());
//        System.out.println(track.getGenre().getName());
//        return track;
//    }


}
