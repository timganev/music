package com.packt.app.playlist;

import com.packt.app.track.Track;
import com.packt.app.track.TrackRepository;
import com.packt.app.user.User;
import com.packt.app.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private PlaylistRepository playlistRepository;
    private TrackRepository trackRepository;
    private UserRepository userRepository;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository, TrackRepository trackRepository, UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.trackRepository = trackRepository;
        this.userRepository = userRepository;
    }




    public Iterable<Playlist> getPlaylists(){
        return playlistRepository.findAll();
    }
    public void save(Playlist playlist){
         playlistRepository.save(playlist);
    }

    public void generatePlaylist(String title, String userName, double duration){
        double currentDuration=0;

        User user=userRepository.findByUsername(userName);

        Playlist playlist=new Playlist(title, user,0);

        while (currentDuration<duration+60){
            Track track=trackRepository.getRandomTrackFromDB();

            if (playlist.getPlaylistTracks()!=null && playlist.getPlaylistTracks().contains(track)){
                continue;
            }
            playlist.getPlaylistTracks().add(track);
            currentDuration+=track.getDuration();
        }
        playlist.setDuration(currentDuration);


        playlistRepository.save(playlist);
    }
}
