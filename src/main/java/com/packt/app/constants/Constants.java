package com.packt.app.constants;

public interface Constants{

    String QUEERY_RANDOM_TRACK="SELECT * FROM track WHERE duration BETWEEN 60 and 300 ORDER BY RAND() limit 1 ";
    String QUERRY_RANDOM_TRACK_BY_GENRE="SELECT * FROM track WHERE genre=:id AND duration BETWEEN 60 and 300 ORDER BY RAND() limit 1";

    String CREATE_PLAYLIST_MESSAGE="Playlist with title %s was created";
    String GET_PLAYLIST_MESSAGE="Get playlist with title %s ";
    String GET_PLAYLIST_TRACKS_MESSAGE="Get tracks of playlist title %s ";
    String SAVE_PLAYLIST_TO_DB_MESSAGE="Playlist with title %s was saved to database ";
    String GET_ALL_PLAYLISTS_MESSAGE="Get all playlists from database ";
    String THROW_WHEN_PLAYLIST_WITH_TITLE_ALREADY_EXIST_MESSAGE="Can not save playlist with this title! Playlist with this title already exist";
    String THROW_WHEN_PLAYLIST_HAS_NO_TRACKS_MESSAGE="This playlist has no tracks";

    String SAVE_TRACK_TO_DB_MESSAGE ="Track with genre - %s was saved to database";
       String DOWNLOADED_TRACKS_WITH_GENRES_SAVED_MESSAGE="Downloaded tracks from external API with genre %s was saved to database";
       String NO_TRACKS_WITH_THIS_DURATION_AND_GENRE="No tracks matched in genre %s with duration %s";

    String DOWNLOADED_GENRES_SAVED_MESSAGE ="Downloaded genres from external API was saved to database";
    String GET_GENRE_BY_NAME_MESSAGE="Get genre with name %s";
    String THIS_GENRE_ALREADY_EXIST="Genre with %s already exist";


}
