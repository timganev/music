package com.packt.app.playlist;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class PlaylistCredentialsList {

    private String username;
    private String title;

    @JsonProperty("data")
    private List<PlaylistCredentials> credentialsList;

    public PlaylistCredentialsList() {
        credentialsList = new ArrayList<>();
    }

    public PlaylistCredentialsList(String username, String title, List<PlaylistCredentials> credentialsList) {
        this.username = username;
        this.title = title;
        this.credentialsList = credentialsList;
    }

    public PlaylistCredentialsList(List<PlaylistCredentials> credentialsList) {
        this.credentialsList = credentialsList;
    }

    public List<PlaylistCredentials> getPlaylistCredentials() {
        return credentialsList;
    }

    public void setPlaylistCredentials(List<PlaylistCredentials> credentialsList) {
        this.credentialsList = credentialsList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
