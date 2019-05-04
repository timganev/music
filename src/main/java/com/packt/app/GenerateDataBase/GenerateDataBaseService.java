package com.packt.app.GenerateDataBase;

import com.packt.app.genre.Genre;
import com.packt.app.track.Track;

import java.util.List;

public interface GenerateDataBaseService {
    List<String> getPlaylistTracks(Genre genre) throws NullPointerException;
    List<Track> getAllTracksByGenre(String genreType);
    Genre getGenre(String genreType);
    void saveData(List<Track> tracks, Genre genre);
}
