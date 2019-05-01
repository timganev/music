package com.packt.app.constants;

public interface Constants{

    String QUEERY_RANDOM_TRACK="SELECT * FROM track WHERE duration BETWEEN 60 and 300 ORDER BY RAND() limit 1 ";
    String QUERRY_RANDOM_TRACK_BY_GENRE="SELECT * FROM track WHERE genre=:id AND duration BETWEEN 60 and 300 ORDER BY RAND() limit 1";

    String CREATE_PLAYLIST_MESSAGE="Playlist with title %s was created";
    String GET_PLAYLIST_MESSAGE="Get playlist with title %s ";
    String GET_PLAYLIST_TRACKS_MESSAGE="Get tracks of playlist title %s ";
    String SAVE_PLAYLIST_TO_DB_MESSAGE="Playlist with title %s was saved to database ";
    String GET_ALL_PLAYLISTS_MESSAGE="Get all playlists from database ";

    String SAVE_TRACK_TO_DB_MESSAGE ="Track with genre - %s was saved to database";
    String DOWNLOADED_RB_TRACKS_SAVED_MESSAGE ="Downloaded tracks from external API with genre R&B was saved to database";
    String DOWNLOADED_DANCE_TRACKS_SAVED_MESSAGE ="Downloaded tracks from external API with genre Dance was saved to database";
    String DOWNLOADED_ROCK_TRACKS_SAVED_MESSAGE ="Downloaded tracks from external API with genre Rock was saved to database";
    String DOWNLOADED_POP_TRACKS_SAVED_MESSAGE ="Downloaded tracks from external API with genre Pop was saved to database";

    String DOWNLOADED_GENRES_SAVED_MESSAGE ="Downloaded genres from external API was saved to database";
    String GET_GENRE_BY_NAME_MESSAGE="Get genre with name %s";


}
