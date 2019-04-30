package com.packt.app.constants;

public interface Constants{

    String QUEERY_RANDOM_TRACK="SELECT * FROM track WHERE duration BETWEEN 60 and 300 ORDER BY RAND() limit 1 ";
    String QUERRY_RANDOM_TRACK_BY_GENRE="SELECT * FROM track WHERE genre=:id AND duration BETWEEN 60 and 300 ORDER BY RAND() limit 1";



}
