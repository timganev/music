package com.packt.app.genre;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.packt.app.track.Track;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;
    private String image_url;


    @OneToMany(cascade = CascadeType.ALL, mappedBy="genre")
    @JsonIgnore
    private Set<Track> tracks;

    public Genre() {

    }

    public Genre(String name, String image_url) {
        this.name = name;
        this.image_url = image_url;
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Set<Track> getTracks() {
        return tracks;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }
}
