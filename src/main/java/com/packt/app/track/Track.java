package com.packt.app.track;


import com.packt.app.album.Album;
import com.packt.app.artist.Artist;
import com.packt.app.genre.Genre;

import javax.persistence.*;

@Entity
public class Track {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String title;
    private String link;
    private int duration;
    private int rank;
    private String preview_url;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity= Genre.class)
    @JoinColumn(name = "genre")
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity= Album.class)
    @JoinColumn(name = "album")
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity= Artist.class)
    @JoinColumn(name = "artist")
    private Artist artist;

    public Track() {

    }

    public Track(String title, String link, int duration, int rank, String preview_url, Genre genre, Album album, Artist artist) {
        this.title = title;
        this.link = link;
        this.duration = duration;
        this.rank = rank;
        this.preview_url = preview_url;
        this.genre = genre;
        this.album = album;
        this.artist = artist;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public void setPreview_url(String preview_url) {
        this.preview_url = preview_url;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
