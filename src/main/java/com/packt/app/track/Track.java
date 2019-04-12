package com.packt.app.track;


import com.packt.app.album.Album;
import com.packt.app.artist.Artist;
import com.packt.app.genre.Genre;

import javax.persistence.*;

@Entity
public class Track {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String title;
    private String link;
    private int duration;
    private int rank;
    private String preview_url;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity= Genre.class)
    @JoinColumn(name = "genre_id")
    private int genre_id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity= Album.class)
    @JoinColumn(name = "album_id")
    private int album_id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity= Artist.class)
    @JoinColumn(name = "artist_id")
    private int artist_id;

    public Track() {

    }

    public Track(String title, String link, int duration, int rank, String preview_url, int album_id, int genre_id) {
        this.title = title;
        this.link = link;
        this.duration = duration;
        this.rank = rank;
        this.preview_url = preview_url;
        this.album_id = album_id;
        this.genre_id = genre_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public void setPreview_url(String preview_url) {
        this.preview_url = preview_url;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }
}
