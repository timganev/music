package com.packt.app.track;

import com.packt.app.genre.Genre;
import com.packt.app.genre.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import static org.springframework.web.bind.annotation.RequestMethod.GET;


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

    //    http://localhost:8080//findbyduration?min=1&max=200
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/findbyduration", params = {"min", "max"}, method = GET)
    @ResponseBody
    public ResponseEntity<List<Track>> findAllByDurationBetween(Integer min, Integer max) {
        if (trackService.findAllByDurationBetween( min,max).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Tracks not found");
        }
        return new ResponseEntity<>(trackService.findAllByDurationBetween( min,max), HttpStatus.OK);

    }

}
