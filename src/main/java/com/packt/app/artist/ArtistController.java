package com.packt.app.artist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ArtistController {

    private ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/artists")
    public ResponseEntity<Iterable<Artist>> getArtists(){
        if (artistService.getArtists().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Artists not found");
        }
        return new  ResponseEntity<>(artistService.getArtists(), HttpStatus.OK);

    }

}
