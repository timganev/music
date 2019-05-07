package com.packt.app.track;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.packt.app.album.Album;
import com.packt.app.artist.Artist;
import com.packt.app.genre.Genre;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String link;
    private int duration;
    private int rank;

    @JsonProperty("preview")
    private String preview_url;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Genre.class, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "genre")
    private Genre genre;

    @JsonProperty("artist")
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Artist.class)
    @JsonIgnore
    @JoinColumn(name = "artist")
    private Artist artist;


    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Album.class)
    @JsonProperty("album")
    @JoinColumn(name = "album")
    private Album album;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
