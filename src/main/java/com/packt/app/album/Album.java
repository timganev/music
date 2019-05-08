package com.packt.app.album;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.packt.app.artist.Artist;
import com.packt.app.track.Track;


import javax.persistence.*;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Album {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String title;

    @JsonProperty("tracklist")
    private String album_tracks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @JsonProperty("tracks")
    @OneToMany(cascade = CascadeType.ALL, mappedBy="album")
    @JsonIgnore
    private Set<Track> albumTracks;


    public Album() {
    }

    public Album(String title, String album_track, Artist artist, Set<Track> albumTracks) {
        this.title = title;
        this.album_tracks = album_track;
        this.artist = artist;
        this.albumTracks = albumTracks;
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

    public String getAlbum_track() {
        return album_tracks;
    }

    public void setAlbum_track(String album_track) {
        this.album_tracks = album_track;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

//    public Set<Track> getAlbumTracks() {
//        return albumTracks;
//    }
//
//    public void setAlbumTracks(Set<Track> albumTracks) {
//        this.albumTracks = albumTracks;
//    }
//
//    public String getAlbum_tracks() {
//        return album_tracks;
//    }
//
//    public void setAlbum_tracks(String album_tracks) {
//        this.album_tracks = album_tracks;
//    }
}