package com.packt.app.playlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.packt.app.userDetails.UserDetails;

import javax.persistence.*;

public class PlaylistDTO {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("creator")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "details_id")
    private UserDetails details_id;

    @JsonProperty("tracklist")
    private String trackUrl;

    private String image_url;

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

    public UserDetails getDetails_id() {
        return details_id;
    }

    public void setDetails_id(UserDetails details_id) {
        this.details_id = details_id;
    }

    public String getTrackUrl() {
        return trackUrl;
    }

    public void setTrackUrl(String trackUrl) {
        this.trackUrl = trackUrl;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
