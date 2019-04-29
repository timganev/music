package com.packt.app.playlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaylistCredentials {

    private String genre;
    private double duration;


    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
}
