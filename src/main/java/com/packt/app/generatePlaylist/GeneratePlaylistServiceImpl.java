package com.packt.app.generatePlaylist;

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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

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


    public void savePlaylist(int avgRank, double durationToSet, Playlist playlist) {
        playlist.setDuration(durationToSet);
        playlist.setAvgrank(avgRank);
        playlistRepository.save(playlist);
    }

    @Override
    public void generatePlaylist(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {
        PlaylistCredentialsList playlistCredentialsList = generate(req, res);
        String title = playlistCredentialsList.getTitle();
        if (playlistRepository.findFirstByTitle(title)!=null){
            String message=String.format(THROW_WHEN_PLAYLIST_WITH_TITLE_ALREADY_EXIST_MESSAGE,title);
            logger.info(message);
            throw new IllegalArgumentException(message);
        }

        String userName = playlistCredentialsList.getUsername();
        boolean isSameArtistAllow = playlistCredentialsList.isSameartist();
        boolean isTopRankAllow = playlistCredentialsList.isTopranks();
        List<PlaylistCredentials> playlistCredentials = playlistCredentialsList.getPlaylistCredentials();
        int playlistCredentialsSize = playlistCredentials.size();

        if (isSameArtistAllow || isTopRankAllow) {
            generatePlaylistWithCriteria(title, userName, playlistCredentials, isSameArtistAllow, isTopRankAllow, playlistCredentialsSize);

        } else {
            generatePlaylistWithoutCriteria(title, userName, playlistCredentials, playlistCredentialsSize);

        }
    }

    public List<Track> getTracksByGenreGroupByTitle(int genreid) {
        return trackRepository.findAllByGenreGroupByTitle(genreid);


    }

    public List<Track> getAllTracksWithoutCiteriaOrderByArtist() {
        return (List<Track>) trackRepository.findAllGroupByArtist();
    }

    public List<Track> getAllTracksWithCiteriaGroupByTitle() {
        return (List<Track>) trackRepository.findAllGroupByTitle();

    }

    public List<Track> getAllByGenreGroupByArtist(int genreId) {
        return (List<Track>) trackRepository.findAllByGenreGroupByArtist(genreId);
    }

    public List<Track> getAllTracksGroupByArtistOrderByRankDesc() {
        return (List<Track>) trackRepository.findAllTracksGroupByArtistOrderByRankDesc();
    }

    public List<Track> getAllTracksByGenreGroupByArtistOrderByRankDesc(int genreId) {
        return (List<Track>) trackRepository.findAllTracksByGenreGroupByArtistOrderByRankDesc(genreId);
    }

    public List<Track> getAllTracksOrderByRankDesc() {
        return (List<Track>) trackRepository.findAllTracksOrderByRankDesc();
    }

    public List<Track> getAllTracksByGenreOrderByRankDesc(int genreId) {
        return (List<Track>) trackRepository.findAllTracksByGenreOrderByRankDesc(genreId);
    }


    public void generatePlaylistWithoutCriteria(String title, String userName, List<PlaylistCredentials> playlistCredentials,
                                                int playlistCredentialsSize) throws SQLException {
        int i = 0;
        double durationToSet = 0;
        List<Track> allNeededtracks = new ArrayList<>();
        int avgRank = 0;
        double currentDuration = 0;
        Set<Track> tracksToAdd = new HashSet<>();


        User user = userRepository.findFirstByUsername(userName);
        Playlist playlist = new Playlist(title, user, durationToSet, avgRank);

        while (i < playlistCredentialsSize) {

            if (playlistCredentials.get(0).getGenre().equals("all")) {
                allNeededtracks = getAllTracksWithoutCiteriaOrderByArtist();
            } else {
                String genre = playlistCredentials.get(i).getGenre();
                Genre genre1 = genreRepository.findByName(genre);
                allNeededtracks = getAllByGenreGroupByArtist(genre1.getId());
            }

            while (playlistCredentials.get(i).getDuration() > currentDuration-20) {
                Random rand = new Random();
                Track randomTrack = allNeededtracks.get(rand.nextInt(allNeededtracks.size()));

               tracksToAdd.add(randomTrack);
                avgRank += randomTrack.getRank();
                currentDuration+=randomTrack.getDuration();
                allNeededtracks.remove(randomTrack);
            }
            i++;
            durationToSet+=currentDuration;
            currentDuration=0;
        }
        Set<Track>playlistTracks=playlist.getPlaylistTracks();
        playlistTracks=tracksToAdd;
         avgRank = avgRank / tracksToAdd.size();
        savePlaylist(avgRank, durationToSet, playlist);

    }

    public void generatePlaylistWithCriteria(String title, String userName, List<PlaylistCredentials> playlistCredentials,
                                             boolean isSameArtistAllow, boolean isTopRankAllow, int playlistCredentialsSize) throws SQLException {
        int i = 0;
        double durationToSet = 0;
        List<Track> allNeededtracks = new ArrayList<>();
        int avgRank = 0;
        double currentDuration=0;
        Set<Track> tracksToAdd = new HashSet<>();

        User user = userRepository.findFirstByUsername(userName);
        Playlist playlist = new Playlist(title, user, durationToSet, avgRank);

        if (isSameArtistAllow) {
            while (i < playlistCredentialsSize) {

                if (playlistCredentials.get(0).getGenre().equals("all")) {
                    allNeededtracks = getAllTracksWithCiteriaGroupByTitle();
                } else {
                    String genre = playlistCredentials.get(i).getGenre();
                    Genre genre1 = genreRepository.findByName(genre);
                    allNeededtracks = getTracksByGenreGroupByTitle(genre1.getId());
                }
                while (playlistCredentials.get(i).getDuration() > currentDuration-20) {
                    Random rand = new Random();
                    Track randomTrack = allNeededtracks.get(rand.nextInt(allNeededtracks.size()));

                    tracksToAdd.add(randomTrack);
                    avgRank += randomTrack.getRank();
                    currentDuration+=randomTrack.getDuration();
                }
                i++;
                durationToSet+=currentDuration;
                currentDuration=0;
            }
            Set<Track>playlistTracks=playlist.getPlaylistTracks();
            playlistTracks=tracksToAdd;
            avgRank = avgRank / tracksToAdd.size();
            savePlaylist(avgRank, durationToSet, playlist);

        } else if (isTopRankAllow) {
            while (i < playlistCredentialsSize) {

                if (playlistCredentials.get(0).getGenre().equals("all")) {
                    allNeededtracks = getAllTracksGroupByArtistOrderByRankDesc();

                } else {
                    String genre = playlistCredentials.get(i).getGenre();
                    Genre genre1 = genreRepository.findByName(genre);
                    allNeededtracks = getAllTracksByGenreGroupByArtistOrderByRankDesc(genre1.getId());

                }

                while (playlistCredentials.get(i).getDuration() > currentDuration-20) {
                    Track randomTrack = allNeededtracks.get(0);

                    tracksToAdd.add(randomTrack);
                    avgRank += randomTrack.getRank();
                    currentDuration+=randomTrack.getDuration();
                    allNeededtracks.remove(randomTrack);
                }
                i++;
                durationToSet+=currentDuration;
                currentDuration=0;
            }
            Set<Track>playlistTracks=playlist.getPlaylistTracks();
            playlistTracks=tracksToAdd;
            avgRank = avgRank / tracksToAdd.size();
            savePlaylist(avgRank, durationToSet, playlist);

        } else {
            while (i < playlistCredentialsSize) {

                if (playlistCredentials.get(0).getGenre().equals("all")) {
                    allNeededtracks = getAllTracksOrderByRankDesc();


                } else {
                    String genre = playlistCredentials.get(i).getGenre();
                    Genre genre1 = genreRepository.findByName(genre);
                    allNeededtracks = getAllTracksByGenreOrderByRankDesc(genre1.getId());

                }

                while (playlistCredentials.get(i).getDuration() > currentDuration-20) {
                    Random rand = new Random();
                    Track randomTrack = allNeededtracks.get(rand.nextInt(allNeededtracks.size()));


                    tracksToAdd.add(randomTrack);
                    avgRank += randomTrack.getRank();
                    currentDuration+=randomTrack.getDuration();
                }
                i++;
                durationToSet+=currentDuration;
                currentDuration=0;
            }
            Set<Track>playlistTracks=playlist.getPlaylistTracks();
            playlistTracks=tracksToAdd;
            avgRank = avgRank / tracksToAdd.size();
            savePlaylist(avgRank, durationToSet, playlist);

        }
    }

    public PlaylistCredentialsList generate(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PlaylistCredentialsList creds = new ObjectMapper()
                .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                .readValue(req.getInputStream(), PlaylistCredentialsList.class);
        return creds;
    }


    public List<Track> getTracksByGenreOrderedByRankDesc(String genre) {
        Genre genre1 = genreRepository.findByName(genre);
        List<Track> tracks = trackRepository.getAllByGenreOrderByRankDesc(genre1);
        return tracks;
    }


}
