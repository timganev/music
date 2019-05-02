package com.packt.app.genre;

import java.util.List;

public interface GenreService {
    Iterable<Genre> getGenres();
    void saveGenres();
    Genre findByName(String name);
    List<Genre> downloadGenresFromAPI();
}
