package com.packt.app.genre;

import com.packt.app.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.packt.app.constants.Constants.DOWNLOADED_GENRES_SAVED_MESSAGE;
import static com.packt.app.constants.Constants.GET_GENRE_BY_NAME_MESSAGE;
import static com.packt.app.constants.Constants.THIS_GENRE_ALREADY_EXIST;

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

    public List<Genre> downloadGenresFromAPI(){
        RestTemplate restTemplate = new RestTemplate();
        GenreList response = restTemplate.getForObject(
                "https://api.deezer.com/genre",
                GenreList.class);
        return response.getGenres();
    }

    public void saveGenres(){
        List<Genre> genres = downloadGenresFromAPI();

        List<Genre> existingGenres= (List<Genre>) genreRepository.findAll();
        List<String> names=new ArrayList<>();
        for (Genre existingGenre : existingGenres) {
            names.add(existingGenre.getName());
        }

        for (Genre genre : genres) {
            if (!names.contains(genre.getName())){
                genreRepository.save(genre);
            }else {
                String message=String.format(THIS_GENRE_ALREADY_EXIST,genre.getName());
                logger.error(message);
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
