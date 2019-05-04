package com.packt.app.GeneratePlaylist;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.app.Application;
import com.packt.app.genre.Genre;
import com.packt.app.genre.GenreRepository;
import com.packt.app.playlist.*;
import com.packt.app.track.Track;
import com.packt.app.track.TrackRepository;
import com.packt.app.user.User;
import com.packt.app.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.packt.app.constants.Constants.*;

@Service
public class GeneratePlaylistServiceImpl implements GeneratePlaylistService {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private PlaylistRepository playlistRepository;
    private TrackRepository trackRepository;
    private UserRepository userRepository;
    private GenreRepository genreRepository;

    @Autowired
    public GeneratePlaylistServiceImpl(PlaylistRepository playlistRepository, TrackRepository trackRepository,
                               UserRepository userRepository, GenreRepository genreRepository) {
        this.playlistRepository = playlistRepository;
        this.trackRepository = trackRepository;
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
    }


    @Override
    public void saveGeneratedPlaylistByOneGenre(HttpServletRequest req, HttpServletResponse res) throws IOException{
        Playlist playlist=generatePlaylistByOneGenre(req,res);

        playlistRepository.save(playlist);
        String message=String.format(CREATE_PLAYLIST_MESSAGE, playlist.getTitle());
        logger.debug(message);
    }

    @Override
    public void saveGeneratedPlaylistByMoreGenre(HttpServletRequest req, HttpServletResponse res) throws IOException{
        Playlist playlist=generatePlaylistByMoreGenres(req,res);

        playlistRepository.save(playlist);
        String message=String.format(CREATE_PLAYLIST_MESSAGE, playlist.getTitle());
        logger.debug(message);
    }

    @Override
    public void saveGeneratedPlaylist(HttpServletRequest req, HttpServletResponse res) throws IOException{
        Playlist playlist=generatePlaylist(req,res);

        playlistRepository.save(playlist);
        String message=String.format(CREATE_PLAYLIST_MESSAGE, playlist.getTitle());
        logger.debug(message);
    }


    public Playlist generatePlaylistByOneGenre(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PlaylistCredentialsList playlistCredentialsList = generate(req, res);
        String title = playlistCredentialsList.getTitle();
        String userName = playlistCredentialsList.getUsername();
        List<PlaylistCredentials> playlistCredentials = playlistCredentialsList.getPlaylistCredentials();

        double currentDuration = 0;
        User user = userRepository.findByUsername(userName);
        Playlist playlist = new Playlist(title, user, 0);
        Track track = new Track();

            int countOfRandomReturns=0;
            PlaylistCredentials pl = playlistCredentials.get(0);
            String genre = pl.getGenre();

            currentDuration = getCurrentDuration(currentDuration, playlist, countOfRandomReturns, pl, genre);

        if (currentDuration==0){
            String message=String.format("No tracks matched in genre %s with duration", pl.getGenre(),pl.getDuration());
            logger.debug(message);

        }
            playlist.setDuration(currentDuration);

            if (playlistRepository.findByTitle(title)!=null) {
                String message=String.format(THROW_WHEN_PLAYLIST_WITH_TITLE_ALREADY_EXIST_MESSAGE, title);
                logger.error(message);
                throw new IllegalArgumentException("Playlist with this title already exist");

            }

        if (playlist.getPlaylistTracks().isEmpty()){
            logger.error("Have not tracks to get in playlist with this duration and genres");
            throw new NullPointerException("Have not tracks to get in playlist with this duration and genres");
        }
            playlist.setUsername(userName);
            return playlist;

    }


    public Playlist generatePlaylistByMoreGenres(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PlaylistCredentialsList playlistCredentialsList = generate(req, res);
        String title = playlistCredentialsList.getTitle();
        String userName = playlistCredentialsList.getUsername();
        List<PlaylistCredentials> playlistCredentials = playlistCredentialsList.getPlaylistCredentials();


        double currentDuration = 0;
        User user = userRepository.findByUsername(userName);

        Playlist playlist = new Playlist(title, user, 0);
        Track track = new Track();

            int currentCredential = 0;
            int countOfRandomReturns = 0;
            int durationToSet=0;
            while (currentCredential < playlistCredentials.size()) {
                PlaylistCredentials pl = playlistCredentials.get(currentCredential);
                String genre = pl.getGenre();

                currentDuration = getCurrentDuration(currentDuration, playlist,
                        countOfRandomReturns, pl, genre);

                if (currentDuration==0){
                    String message=String.format("No tracks matched in genre %s with duration", pl.getGenre(),pl.getDuration());
                    logger.debug(message);
                    currentCredential++;
                    continue;
                }
                durationToSet+=currentDuration;
                currentDuration=0;
                playlist.setDuration(durationToSet);
                currentCredential++;
            }

        if (playlistRepository.findByTitle(title)!=null) {
            String message=String.format(THROW_WHEN_PLAYLIST_WITH_TITLE_ALREADY_EXIST_MESSAGE, title);
            logger.error(message);
            throw new IllegalArgumentException("Playlist with this title already exist");

        }

        if (playlist.getPlaylistTracks().isEmpty()){
            logger.error("Have not tracks to get in playlist with this duration and genres");
            throw new NullPointerException("Have not tracks to get in playlist with this duration and genres");
        }

        playlist.setUsername(userName);
        return playlist;

//            System.out.println(playlist.getPlaylistGenres());

    }


    public double getCurrentDuration(double currentDuration, Playlist playlist, int countOfRandomReturns,
                                      PlaylistCredentials pl, String genre) {
        Genre genre1=genreRepository.findByName(genre);

        while (currentDuration < pl.getDuration() +60) {

            if (countOfRandomReturns >= 5) {
                break;
            }
            Track track;

            if ("all".equals(genre)) {
                track = trackRepository.getRandomTrackFromDB();
            } else {
                track =trackRepository.getRandomTrackFromDbByGenre(genre1.getId());
            }
            if (track.getDuration()>pl.getDuration()+120){
                break;
            }


            if (playlist.getPlaylistTracks() != null && playlist.getPlaylistTracks().contains(track)) {
                while (playlist.getPlaylistTracks().contains(track)){
                    track =trackRepository.getRandomTrackFromDbByGenre(genre1.getId());
                    countOfRandomReturns++;
                    if (countOfRandomReturns>5){
                        break;
                    }
                }
            }


            if (playlist.getPlaylistArtists() != null && playlist.getPlaylistArtists().contains(track.getArtist())) {
                continue;
            }
            playlist.getPlaylistTracks().add(track);
            playlist.getPlaylistArtists().add(track.getArtist());
            playlist.getPlaylistGenres().add(track.getGenre());
            currentDuration += track.getDuration();
        }
        return currentDuration;
    }

    public Playlist generatePlaylist(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PlaylistCredentialsList playlistCredentialsList = generate(req, res);
        String title = playlistCredentialsList.getTitle();
        String userName = playlistCredentialsList.getUsername();
        List<PlaylistCredentials> playlistCredentials = playlistCredentialsList.getPlaylistCredentials();
        PlaylistCredentials pl = playlistCredentials.get(0);

        double currentDuration = 0;
        User user = userRepository.findByUsername(userName);

        Playlist playlist = new Playlist(title, user, 0);
        Track track = new Track();

        int countOfRandomReturns = 0;
        int durationToSet=0;

        track =trackRepository.getRandomTrackFromDB();

        while (currentDuration < pl.getDuration() +120) {
            if (track.getDuration() > pl.getDuration() + 120) {
                break;
            }

            if (playlist.getPlaylistTracks() != null && playlist.getPlaylistTracks().contains(track)) {
                while (playlist.getPlaylistTracks().contains(track)){
                    track =trackRepository.getRandomTrackFromDB();
                    countOfRandomReturns++;
                    if (countOfRandomReturns>5){
                        break;
                    }
                }
            }

            if (playlist.getPlaylistTracks() != null && playlist.getPlaylistTracks().contains(track)) {
                countOfRandomReturns++;
                continue;
            }
            if (playlist.getPlaylistArtists() != null && playlist.getPlaylistArtists().contains(track.getArtist())) {
                continue;
            }
            playlist.getPlaylistTracks().add(track);
            playlist.getPlaylistArtists().add(track.getArtist());
            playlist.getPlaylistGenres().add(track.getGenre());
            currentDuration += track.getDuration();
        }

        if (currentDuration==0){
            String message=String.format("No tracks matched in genre %s with duration", pl.getGenre(),pl.getDuration());
            logger.debug(message);
            throw new NullPointerException();
        }

        durationToSet+=currentDuration;
        playlist.setDuration(durationToSet);

        if (playlistRepository.findByTitle(title)!=null) {
            String message=String.format(THROW_WHEN_PLAYLIST_WITH_TITLE_ALREADY_EXIST_MESSAGE, title);
            logger.error(message);
            throw new IllegalArgumentException("Playlist with this title already exist");
        }

        if (playlist.getPlaylistTracks().isEmpty()){
            logger.error("Have not tracks to get in playlist with this duration and genres");
            throw new NullPointerException("Have not tracks to get in playlist with this duration");
        }

        playlist.setUsername(userName);
        return playlist;

    }


    public PlaylistCredentialsList generate(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PlaylistCredentialsList creds = new ObjectMapper()
                .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                .readValue(req.getInputStream(), PlaylistCredentialsList.class);
        return creds;
    }


    public Set<Track> getTracksByGenreOrderedByRankDesc(String genre){
        Genre genre1=genreRepository.findByName(genre);
        Set<Track> tracks=trackRepository.getAllByGenreOrderByRankDesc(genre1);
        System.out.println();
        return tracks;
    }


}
