package com.packt.app.album;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.packt.app.artist.Artist;
import com.packt.app.track.Track;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Album {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String title;
    private String album_track;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="album_id")
    @JsonIgnore
    private Set<Track> albumTracks;


    public Album() {
    }

    public Album(String title, String album_track, Artist artist, Set<Track> albumTracks) {
        this.title = title;
        this.album_track = album_track;
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
        return album_track;
    }

    public void setAlbum_track(String album_track) {
        this.album_track = album_track;
    }


}