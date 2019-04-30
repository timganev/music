package com.packt.app.playlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.app.genre.Genre;
import com.packt.app.genre.GenreRepository;
import com.packt.app.track.Track;
import com.packt.app.track.TrackRepository;
import com.packt.app.user.User;
import com.packt.app.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private PlaylistRepository playlistRepository;
    private TrackRepository trackRepository;
    private UserRepository userRepository;
    private GenreRepository genreRepository;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository, TrackRepository trackRepository,
                               UserRepository userRepository, GenreRepository genreRepository) {
        this.playlistRepository = playlistRepository;
        this.trackRepository = trackRepository;
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
    }






    @Override
    public Iterable<Playlist> getPlaylists(){
        return playlistRepository.findAll();
    }
    @Override
    public void save(Playlist playlist){
         playlistRepository.save(playlist);
    }

    @Override
    public void generatePlaylistByOneGenre(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PlaylistCredentialsList playlistCredentialsList = generate(req, res);
        String title = playlistCredentialsList.getTitle();
        String userName = playlistCredentialsList.getUsername();
        List<PlaylistCredentials> playlistCredentials = playlistCredentialsList.getPlaylistCredentials();

        double currentDuration = 0;
        User user = userRepository.findByUsername(userName);
        Playlist playlist = new Playlist(title, user, 0);
        Track track = new Track();

        if (playlistCredentials.size() == 1) {

            int countOfRandomReturns=0;
            PlaylistCredentials pl = playlistCredentials.get(0);
            String genre = pl.getGenre();

            currentDuration = getCurrentDuration(currentDuration, playlist, track, countOfRandomReturns, pl, genre);
            playlist.setDuration(currentDuration);

            playlistRepository.save(playlist);

        }
    }

    @Override
    public void generatePlaylistByMoreGenres(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PlaylistCredentialsList playlistCredentialsList = generate(req, res);
        String title = playlistCredentialsList.getTitle();
        String userName = playlistCredentialsList.getUsername();
        List<PlaylistCredentials> playlistCredentials = playlistCredentialsList.getPlaylistCredentials();

        double currentDuration = 0;
        User user = userRepository.findByUsername(userName);
        Playlist playlist = new Playlist(title, user, 0);
        Track track = new Track();

        if (playlistCredentials.size() > 1) {

            int currentCredential = 0;
            int countOfRandomReturns = 0;
            int durationToSet=0;
            while (currentCredential < playlistCredentials.size()) {
                PlaylistCredentials pl = playlistCredentials.get(currentCredential);
                String genre = pl.getGenre();

                currentDuration = getCurrentDuration(currentDuration, playlist,
                        track, countOfRandomReturns, pl, genre);
                durationToSet+=currentDuration;
                currentDuration=0;
                playlist.setDuration(durationToSet);
                currentCredential++;
            }
            playlistRepository.save(playlist);
            System.out.println(playlist.getPlaylistGenres());

        }
    }

    private double getCurrentDuration(double currentDuration, Playlist playlist, Track track, int countOfRandomReturns,
                                      PlaylistCredentials pl, String genre) {

        Genre genre1=genreRepository.findByName(genre);

        while (currentDuration < pl.getDuration() + 5) {

            if (countOfRandomReturns >= 5) {
                break;
            }
            if ("all".equals(genre)) {
                track = trackRepository.getRandomTrackFromDB();

            } else {
                track=trackRepository.getRandomTrackFromDbByGenre(genre1.getId());
            }

            if (playlist.getPlaylistTracks() != null && playlist.getPlaylistTracks().contains(track)) {
                countOfRandomReturns++;
                continue;
            }
            playlist.getPlaylistTracks().add(track);
            playlist.getPlaylistGenres().add(track.getGenre());
            currentDuration += track.getDuration();
        }
        return currentDuration;
    }


    @Override
    public PlaylistCredentialsList generate(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PlaylistCredentialsList creds = new ObjectMapper()
                .readValue(req.getInputStream(), PlaylistCredentialsList.class);
        return creds;
    }

    public List<Playlist> getPlaylistByTitle(String title){
       return playlistRepository.findByTitle(title);
    }
    public Set<Track> getPlaylistTracks(String title){
       return playlistRepository.findByTitle(title).get(0).getPlaylistTracks();
    }


}
