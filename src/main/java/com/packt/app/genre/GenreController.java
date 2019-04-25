package com.packt.app.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenreController {

    private GenreServiceImpl genreService;

    @Autowired
    public GenreController(GenreServiceImpl genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public Iterable<Genre> getGenres() {
        return genreService.getGenres();
    }

    @GetMapping("/savegenre")
    public void saveGenres() {
       genreService.saveGenres();
    }

    @GetMapping("/genre/{name}")
    public Genre findByName(@PathVariable String name){
      return genreService.findByName(name);
    }


}
