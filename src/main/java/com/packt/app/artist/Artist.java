package com.packt.app.artist;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.packt.app.album.Album;
import com.packt.app.track.Track;


import javax.persistence.*;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String name;

    @JsonProperty("tracklist")
    private String artist_track_url;


    @OneToMany(cascade = CascadeType.ALL, mappedBy="artist")
    @JsonIgnore
    private Set<Album> artistAlbums;

    @JsonProperty("tracklist")
    @OneToMany(cascade = CascadeType.ALL, mappedBy="artist")
    @JsonIgnore
    private Set<Track> artistTracks;

    public Artist( String name, String artist_track_url) {
        this.name = name;
        this.artist_track_url = artist_track_url;
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

    public String getArtist_track_url() {
        return artist_track_url;
    }

    public void setArtist_track_url(String artist_track_url) {
        this.artist_track_url = artist_track_url;
    }

    public Set<Album> getArtistAlbums() {
        return artistAlbums;
    }

    public void setArtistAlbums(Set<Album> artistAlbums) {
        this.artistAlbums = artistAlbums;
    }

//
//    public Set<Track> getArtistTracks() {
//        return artistTracks;
//    }
//
//    public void setArtistTracks(Set<Track> artistTracks) {
//        this.artistTracks = artistTracks;
//    }

}
