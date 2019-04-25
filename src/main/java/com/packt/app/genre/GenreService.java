package com.packt.app.genre;

public interface GenreService {
    Iterable<Genre> getGenres();
    void saveGenres();
    Genre findByName(String name);
}
