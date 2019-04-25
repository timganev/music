package com.packt.app.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {


    private GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Iterable<Genre> getGenres(){
      return genreRepository.findAll();
    }

    public void saveGenres(){
        RestTemplate restTemplate = new RestTemplate();
        GenreList response = restTemplate.getForObject(
                "https://api.deezer.com/genre",
                GenreList.class);
        List<Genre> genres = response.getGenres();

        List<Genre> existingGenres= (List<Genre>) genreRepository.findAll();

        for (Genre genre : genres) {
            if (!existingGenres.contains(genre)){
                genreRepository.save(genre);
            }else {
                throw new IllegalArgumentException("This genre already exist");
            }
        }
    }

    @Override
    public Genre findByName(String name) {
        return genreRepository.findByName(name);
    }


}
