package com.packt.app.album;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.packt.app.artist.Artist;
import com.packt.app.track.Track;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Album {

    @Id
    private Integer id;

    @Size(min = 1,max = 30,message = "Title must be between 1 and 30 symbols")
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
        setTitle(title);
        this.album_tracks = album_track;
        this.artist = artist;
        this.albumTracks = albumTracks;
    }


    public Integer getId() {
        return id;
    }

    public void setId( Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(@Valid String title) {
        this.title = title;
    }

//    public String getAlbum_track() {
//        return album_tracks;
//    }
//
//    public void setAlbum_track(String album_track) {
//        this.album_tracks = album_track;
//    }

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
}