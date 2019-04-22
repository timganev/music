package com.packt.app.genre;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.packt.app.track.Track;


import javax.persistence.*;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)

public class Genre {

    @Id
    private Long id;

    @JsonProperty("name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="genre")
    @JsonIgnore
    private Set<Track> tracks;

    public Genre() {

    }

    public Genre(long id,String name) {
        this.id=id;
        this.name = name;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<Track> getTracks() {
        return tracks;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }
}
