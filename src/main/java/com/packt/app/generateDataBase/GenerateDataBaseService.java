package com.packt.app.generateDataBase;

import com.packt.app.genre.Genre;
import com.packt.app.track.Track;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.util.List;

public interface GenerateDataBaseService {
    List<String> getPlaylistTracks(Genre genre) throws NullPointerException, JSONException;
    List<Track> getAllTracksByGenre(String genreType);
    Genre getGenre(String genreType);
    void saveData(List<Track> tracks, Genre genre);
}
