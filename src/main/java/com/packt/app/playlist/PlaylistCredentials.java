package com.packt.app.playlist;

public class PlaylistCredentials {

    private String genre;
    private double duration;



    public PlaylistCredentials(String genre, double duration) {
        this.genre = genre;
        this.duration = duration;
    }
    public PlaylistCredentials() {

    }

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
