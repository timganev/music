package com.packt.app.generatePlaylist;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.app.Application;

import com.packt.app.genre.Genre;
import com.packt.app.genre.GenreRepository;
import com.packt.app.playlist.*;
import com.packt.app.playlistImageGenerator.PlaylistImageGeneratorService;
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
    private PlaylistImageGeneratorService playlistImage;

    @Autowired
    public GeneratePlaylistServiceImpl(PlaylistRepository playlistRepository, TrackRepository trackRepository,
                                       UserRepository userRepository, GenreRepository genreRepository,
                                       PlaylistImageGeneratorService playlistImage) {
        this.playlistRepository = playlistRepository;
        this.trackRepository = trackRepository;
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
        this.playlistImage = playlistImage;
    }


    public void savePlaylist(Playlist playlist) {
        playlistRepository.save(playlist);
        String message = String.format(CREATE_PLAYLIST_MESSAGE, playlist.getTitle());
        logger.info(message);
    }

    @Override
    public void generatePlaylist(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {
        PlaylistCredentialsList playlistCredentialsList = generate(req, res);
        String title = playlistCredentialsList.getTitle();
        if (playlistRepository.findFirstByTitle(title) != null) {
            String message = String.format(THROW_WHEN_PLAYLIST_WITH_TITLE_ALREADY_EXIST_MESSAGE, title);
            logger.info(message);
            throw new IllegalArgumentException(message);
        }

        String userName = playlistCredentialsList.getUsername();
        boolean isSameArtistAllow = playlistCredentialsList.isSameartist();
        boolean isTopRankAllow = playlistCredentialsList.isTopranks();
        List<PlaylistCredentials> playlistCredentials = playlistCredentialsList.getPlaylistCredentials();
        int playlistCredentialsSize = playlistCredentials.size();

        generatePlaylistWithCriteria(title, userName, playlistCredentials, isSameArtistAllow, isTopRankAllow, playlistCredentialsSize);

    }

    public List<Track> getTracksByGenreGroupByTitle(int genreid) {
        return trackRepository.findAllByGenreGroupByTitle(genreid);

    }

    public List<Track> getAllByGenreGroupByArtist(int genreId) {
        return (List<Track>) trackRepository.findAllByGenreGroupByArtist(genreId);
    }


    public List<Track> getAllTracksByGenreGroupByArtistOrderByRankDesc(int genreId) {
        return (List<Track>) trackRepository.findAllTracksByGenreGroupByArtistOrderByRankDesc(genreId);
    }


    public List<Track> getAllTracksByGenreOrderByRankDesc(int genreId) {
        return (List<Track>) trackRepository.findAllTracksByGenreOrderByRankDesc(genreId);
    }


    public void generatePlaylistWithCriteria(String title, String userName, List<PlaylistCredentials> playlistCredentials,
                                             boolean isSameArtistAllow, boolean isTopRankAllow, int playlistCredentialsSize) throws SQLException {
        int i = 0;
        double durationToSet = 0;
        int avgRank = 0;
        double currentDuration = 0;
        Set<Track> tracksToAdd = new HashSet<>();
        User user = userRepository.findFirstByUsername(userName);
        Playlist playlist = new Playlist(title, user, durationToSet, avgRank, "", "");
        String playlistGenres = "";

        if (isSameArtistAllow && !isTopRankAllow) {

            playlistLogic playlistLogic = new playlistLogic(playlistCredentials, playlistCredentialsSize, i, durationToSet, avgRank,
                    currentDuration, tracksToAdd, playlistGenres, "one").invoke();
            durationToSet = playlistLogic.getDurationToSet();
            avgRank = playlistLogic.getAvgRank();
            playlistGenres = playlistLogic.getPlaylistGenres();

        } else if (isTopRankAllow && !isSameArtistAllow) {

            playlistLogic playlistLogic = new playlistLogic(playlistCredentials, playlistCredentialsSize, i, durationToSet, avgRank,
                    currentDuration, tracksToAdd, playlistGenres, "two").invoke();
            durationToSet = playlistLogic.getDurationToSet();
            avgRank = playlistLogic.getAvgRank();
            playlistGenres = playlistLogic.getPlaylistGenres();

        } else if (isSameArtistAllow && isTopRankAllow) {

            playlistLogic playlistLogic = new playlistLogic(playlistCredentials, playlistCredentialsSize, i, durationToSet, avgRank,
                    currentDuration, tracksToAdd, playlistGenres, "three").invoke();
            durationToSet = playlistLogic.getDurationToSet();
            avgRank = playlistLogic.getAvgRank();
            playlistGenres = playlistLogic.getPlaylistGenres();

        } else {
            playlistLogic playlistLogic = new playlistLogic(playlistCredentials, playlistCredentialsSize, i, durationToSet, avgRank,
                    currentDuration, tracksToAdd, playlistGenres, "four").invoke();
            durationToSet = playlistLogic.getDurationToSet();
            avgRank = playlistLogic.getAvgRank();
            playlistGenres = playlistLogic.getPlaylistGenres();
        }

        if (tracksToAdd.isEmpty()) {
            String message = String.format(THROW_WHEN_PLAYLIST_NOT_MATCHED_TRACKS_MESSAGE);
            logger.info(message);
            throw new IllegalArgumentException(message);
        }

        playlist.setUsername(userName);
        playlist.setGenres(playlistGenres);
        playlist.setImage_url(playlistImage.getImageUrl());
        playlist.setPlaylistTracks(tracksToAdd);
        avgRank = avgRank / tracksToAdd.size();
        playlist.setAvgrank(avgRank);
        playlist.setDuration(durationToSet);
        savePlaylist(playlist);
    }


    public Genre findGenreByName(String name) {
        return genreRepository.findByName(name);
    }


    private PlaylistCredentialsList generate(HttpServletRequest req, HttpServletResponse res) throws IOException {
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


    private class playlistLogic {
        private List<PlaylistCredentials> playlistCredentials;
        private int playlistCredentialsSize;
        private int i;
        private double durationToSet;
        private int avgRank;
        private double currentDuration;
        private Set<Track> tracksToAdd;
        private String playlistGenres;
        private String position;

        private playlistLogic(List<PlaylistCredentials> playlistCredentials, int playlistCredentialsSize, int i, double durationToSet,
                              int avgRank, double currentDuration, Set<Track> tracksToAdd, String playlistGenres, String position) {
            this.playlistCredentials = playlistCredentials;
            this.playlistCredentialsSize = playlistCredentialsSize;
            this.i = i;
            this.durationToSet = durationToSet;
            this.avgRank = avgRank;
            this.currentDuration = currentDuration;
            this.tracksToAdd = tracksToAdd;
            this.playlistGenres = playlistGenres;
            this.position = position;
        }

        private double getDurationToSet() {
            return durationToSet;
        }

        private int getAvgRank() {
            return avgRank;
        }

        private String getPlaylistGenres() {
            return playlistGenres;
        }

        private playlistLogic invoke() {
            List<Track> allNeededtracks = new ArrayList<>();
            while (i < playlistCredentialsSize) {
                Genre genre1 = findGenreByName(playlistCredentials.get(i).getGenre());

                switch (position) {
                    case "four":
                        allNeededtracks = getAllByGenreGroupByArtist(genre1.getId());
                        break;
                    case "three":
                        allNeededtracks = getAllTracksByGenreOrderByRankDesc(genre1.getId());
                        break;
                    case "two":
                        allNeededtracks = getAllTracksByGenreGroupByArtistOrderByRankDesc(genre1.getId());
                        break;
                    case "one":
                        allNeededtracks = getTracksByGenreGroupByTitle(genre1.getId());
                        break;
                }
                while (playlistCredentials.get(i).getDuration() - 120 > currentDuration) {
                    Random rand = new Random();
                    Track randomTrack = allNeededtracks.get(rand.nextInt(allNeededtracks.size()));

                    tracksToAdd.add(randomTrack);
                    avgRank += randomTrack.getRank();
                    currentDuration += randomTrack.getDuration();
                    allNeededtracks.remove(randomTrack);
                }
                i++;
                durationToSet += currentDuration;
                currentDuration = 0;
                playlistGenres += genre1.getName() + " ";
            }
            return this;
        }
    }
}
