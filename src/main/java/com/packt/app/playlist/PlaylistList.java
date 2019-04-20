package com.packt.app.playlist;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.packt.app.genre.Genre;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaylistList {


    @JsonProperty("data")
    private List<PlaylistDTO> playlist;

    public PlaylistList() {
        playlist = new ArrayList<>();
    }

    public PlaylistList(List<PlaylistDTO> playlist) {
        this.playlist = playlist;
    }

    public List<PlaylistDTO> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<PlaylistDTO> playlist) {
        this.playlist = playlist;
    }
}
