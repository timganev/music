package com.packt.app.playlist;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.packt.app.genre.Genre;
import com.packt.app.track.Track;
import com.packt.app.user.User;
import com.packt.app.userDetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Playlist {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonIgnore
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("creator")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User userId;

    private String image_url;

    private double duration;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "playlists_genres",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id"))
    private Set<Genre> playlistGenres;


    @JsonProperty("tracks")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "playlists_tracks",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id"))
    private Set<Track> playlistTracks;



    public Playlist() {

    }

    public Playlist(String title, User userId, double duration) {
        this.title = title;
        this.userId = userId;
        this.duration = duration;
        this.playlistTracks=new HashSet<>();
        this.playlistGenres=new HashSet<>();
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

//
//    public User getUserId() {
//        return userId;
//    }
//
//    public void setUserId(User userId) {
//        this.userId = userId;
//    }

//    public Set<Genre> getPlaylistGenres() {
//        return playlistGenres;
//    }
//
//    public void setPlaylistGenres(Set<Genre> playlistGenres) {
//        this.playlistGenres = playlistGenres;
//    }
//
//    public Set<Track> getPlaylistTracks() {
//        return playlistTracks;
//    }
//
//    public void setPlaylistTracks(Set<Track> playlistTracks) {
//        this.playlistTracks = playlistTracks;
//    }


    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public Set<Genre> getPlaylistGenres() {
        return playlistGenres;
    }

    public void setPlaylistGenres(Set<Genre> playlistGenres) {
        this.playlistGenres = playlistGenres;
    }

    public Set<Track> getPlaylistTracks() {
        return playlistTracks;
    }

    public void setPlaylistTracks(Set<Track> playlistTracks) {
        this.playlistTracks = playlistTracks;
    }
}