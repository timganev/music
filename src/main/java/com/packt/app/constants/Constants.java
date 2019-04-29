package com.packt.app.constants;

public interface Constants{

    String QUEERY_RANDOM_TRACK="SELECT * FROM track WHERE duration BETWEEN 60 and 300 ORDER BY RAND() limit 1 ";
    String QUERRY_RANDOM_TRACK_BY_ROCK_GENRE="SELECT * FROM track WHERE genre=152 AND duration BETWEEN 60 and 300 ORDER BY RAND() limit 1";
    String QUERRY_RANDOM_TRACK_BY_DANCE_GENRE="SELECT * FROM track WHERE genre=113 AND duration BETWEEN 60 and 300 ORDER BY RAND() limit 1";
    String QUERRY_RANDOM_TRACK_BY_RB_GENRE="SELECT * FROM track WHERE genre=165 AND duration BETWEEN 60 and 300 ORDER BY RAND() limit 1";
    String QUERRY_RANDOM_TRACK_BY_POP_GENRE="SELECT * FROM track WHERE genre=132 AND duration BETWEEN 60 and 300 ORDER BY RAND() limit 1";


}
