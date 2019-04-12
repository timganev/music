package com.packt.app.artist;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.packt.app.album.Album;
import com.packt.app.track.Track;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String name;
    private String artist_tack_url;


    @OneToMany(cascade = CascadeType.ALL, mappedBy="artist")
    @JsonIgnore
    private Set<Album> artistAlbums;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="artist")
    @JsonIgnore
    private Set<Track> artistTracks;

    public Artist(int id, String name, String artist_tack_url, int album_id) {
        this.id = id;
        this.name = name;
        this.artist_tack_url = artist_tack_url;
    }


    public Artist() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist_tack_url() {
        return artist_tack_url;
    }

    public void setArtist_tack_url(String artist_tack_url) {
        this.artist_tack_url = artist_tack_url;
    }

    public Set<Album> getArtistAlbums() {
        return artistAlbums;
    }

    public void setArtistAlbums(Set<Album> artistAlbums) {
        this.artistAlbums = artistAlbums;
    }

    public Set<Track> getArtistTracks() {
        return artistTracks;
    }

    public void setArtistTracks(Set<Track> artistTracks) {
        this.artistTracks = artistTracks;
    }
}
