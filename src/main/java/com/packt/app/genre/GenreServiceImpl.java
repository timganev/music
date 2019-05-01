package com.packt.app.genre;

import com.packt.app.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.packt.app.constants.Constants.CREATE_PLAYLIST_MESSAGE;
import static com.packt.app.constants.Constants.DOWNLOADED_GENRES_SAVED_MESSAGE;
import static com.packt.app.constants.Constants.GET_GENRE_BY_NAME_MESSAGE;

@Service
public class GenreServiceImpl implements GenreService {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
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

        logger.info(DOWNLOADED_GENRES_SAVED_MESSAGE);

    }

    @Override
    public Genre findByName(String name) {
        String message=String.format(GET_GENRE_BY_NAME_MESSAGE, name);
        logger.info(message);
        return genreRepository.findByName(name);
    }


}
