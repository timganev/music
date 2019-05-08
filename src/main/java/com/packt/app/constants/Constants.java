package com.packt.app.constants;

public interface Constants {


    String CREATE_PLAYLIST_MESSAGE = "Playlist with title %s was created";
    String GET_PLAYLIST_MESSAGE = "Get playlist with title %s ";
    String GET_PLAYLIST_TRACKS_MESSAGE = "Get tracks of playlist title %s ";
    String SAVE_PLAYLIST_TO_DB_MESSAGE = "Playlist with title %s was saved to database ";
    String GET_ALL_PLAYLISTS_MESSAGE = "Get all playlists from database ";
    String THROW_WHEN_PLAYLIST_WITH_TITLE_ALREADY_EXIST_MESSAGE = "Can not save playlist with title %s! Playlist with this title already exist";
    String THROW_WHEN_PLAYLIST_HAS_NO_TRACKS_MESSAGE = "This playlist has no tracks";

    String SAVE_TRACK_TO_DB_MESSAGE = "Track with genre - %s was saved to database";

    String DOWNLOADED_GENRES_SAVED_MESSAGE = "Downloaded genres from external API was saved to database";
    String GET_GENRE_BY_NAME_MESSAGE = "Get genre with name %s";
    String THIS_GENRE_ALREADY_EXIST = "Genre with %s already exist";


    String  USERNAME_WITH_THIS_USERNAME_EXIST= "User with username %s already exist";


}
