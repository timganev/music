package com.packt.app.playlist;


import com.packt.app.Application;

import com.packt.app.genre.GenreRepository;
import com.packt.app.track.Track;
import com.packt.app.track.TrackRepository;
import com.packt.app.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.packt.app.constants.Constants.*;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private PlaylistRepository playlistRepository;


    private List<String> playlistTitles=new ArrayList<>();

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;

    }

    public List<String> getPlaylistTitles() {
        return playlistTitles;
    }

    public void setPlaylistTitles(List<String> playlistTitles) {
        this.playlistTitles = playlistTitles;
    }

    @Override
    public List<Playlist> getPlaylists(){
        logger.info(GET_ALL_PLAYLISTS_MESSAGE);
        return (List<Playlist>) playlistRepository.findAll();
    }

    @Override
    public void save(Playlist playlist){
        String message=String.format(SAVE_PLAYLIST_TO_DB_MESSAGE, playlist.getTitle());
        logger.info(message);
         playlistRepository.save(playlist);
    }


    @Override
    public Playlist getPlaylistByTitle(String title){
        String message=String.format(GET_PLAYLIST_MESSAGE, title);
        logger.info(message);
       return playlistRepository.findByTitle(title);
    }

    @Override
    public Set<Track> getPlaylistTracks(String title){
        if (playlistRepository.findByTitle(title).getPlaylistTracks().isEmpty()){
            logger.error(THROW_WHEN_PLAYLIST_HAS_NO_TRACKS_MESSAGE);
            throw new NullPointerException("This playlist has no tracks");
        }
        String message=String.format(GET_PLAYLIST_TRACKS_MESSAGE, title);
        logger.info(message);
       return playlistRepository.findByTitle(title).getPlaylistTracks();
    }

    @Override
    public List<Playlist> findByUserId_Username(String username) {

        return playlistRepository.findByUserId_Username(username);
    }


}
