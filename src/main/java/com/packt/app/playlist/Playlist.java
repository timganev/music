package com.packt.app.playlist;


import com.packt.app.genre.Genre;
import com.packt.app.track.Track;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Playlist {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String title;
    private int user_id;
    private String image_url;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "playlists_genres",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id"))
    private Set<Genre> playlistGenres;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "playlists_tracks",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id"))
    private Set<Track> playlistTracks;

    public Playlist() {

    }

    public Playlist(String title, int user_id, String image_url) {
        this.title = title;
        this.user_id = user_id;
        this.image_url = image_url;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}