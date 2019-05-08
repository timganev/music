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
    private PlaylistImageGeneratorService playlistImage;

    @Autowired
    public GeneratePlaylistServiceImpl(PlaylistRepository playlistRepository, TrackRepository trackRepository,
                                       UserRepository userRepository, GenreRepository genreRepository,
                                          PlaylistImageGeneratorService playlistImage) {
        this.playlistRepository = playlistRepository;
        this.trackRepository = trackRepository;
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
        this.playlistImage=playlistImage;
    }


    public void savePlaylist(int avgRank, double durationToSet, Playlist playlist) {
        playlist.setDuration(durationToSet);
        playlist.setAvgrank(avgRank);
        playlistRepository.save(playlist);
        String message=String.format(CREATE_PLAYLIST_MESSAGE,playlist.getTitle());
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
        List<Track> allNeededtracks = new ArrayList<>();
        int avgRank = 0;
        double currentDuration = 0;
        Set<Track> tracksToAdd = new HashSet<>();
        User user = userRepository.findFirstByUsername(userName);
        Playlist playlist = new Playlist(title, user, durationToSet, avgRank);

        if (isSameArtistAllow && !isTopRankAllow) {
            while (i < playlistCredentialsSize) {

                Genre genre1 = findGenreByName(playlistCredentials.get(i).getGenre());

                allNeededtracks = getTracksByGenreGroupByTitle(genre1.getId());

                while (playlistCredentials.get(i).getDuration() - 120 > currentDuration) {
                    Random rand = new Random();
                    Track randomTrack = allNeededtracks.get(rand.nextInt(allNeededtracks.size()));
                    tracksToAdd.add(randomTrack);
                    avgRank += randomTrack.getRank();
                    currentDuration += randomTrack.getDuration();
                }
                i++;
                durationToSet += currentDuration;
                currentDuration = 0;
            }
        } else if (isTopRankAllow && !isSameArtistAllow) {
            while (i < playlistCredentialsSize) {
                Genre genre1 = findGenreByName(playlistCredentials.get(i).getGenre());

                allNeededtracks = getAllTracksByGenreGroupByArtistOrderByRankDesc(genre1.getId());

                while (playlistCredentials.get(i).getDuration() - 120 > currentDuration) {
                    Track randomTrack = allNeededtracks.get(0);
                    tracksToAdd.add(randomTrack);
                    avgRank += randomTrack.getRank();
                    currentDuration += randomTrack.getDuration();
                    allNeededtracks.remove(randomTrack);
                }
                i++;
                durationToSet += currentDuration;
                currentDuration = 0;
            }

        } else if (isSameArtistAllow && isTopRankAllow) {
            while (i < playlistCredentialsSize) {

                Genre genre1 = findGenreByName(playlistCredentials.get(i).getGenre());

                allNeededtracks = getAllTracksByGenreOrderByRankDesc(genre1.getId());

                while (playlistCredentials.get(i).getDuration() - 120 > currentDuration) {
                    Track randomTrack = allNeededtracks.get(0);
                    tracksToAdd.add(randomTrack);
                    avgRank += randomTrack.getRank();
                    currentDuration += randomTrack.getDuration();
                    allNeededtracks.remove(randomTrack);
                }
                i++;
                durationToSet += currentDuration;
                currentDuration = 0;
            }
        } else {
            while (i < playlistCredentialsSize) {

                Genre genre1 = findGenreByName(playlistCredentials.get(i).getGenre());
                allNeededtracks = getAllByGenreGroupByArtist(genre1.getId());


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
            }
        }

        if (tracksToAdd.isEmpty()){
            String message=String.format(THROW_WHEN_PLAYLIST_NOT_MATCHED_TRACKS_MESSAGE);
            logger.info(message);
            throw new IllegalArgumentException(message);
        }
        playlist.setImage_url(playlistImage.getImageUrl());
        playlist.setPlaylistTracks(tracksToAdd);
        avgRank = avgRank / tracksToAdd.size();
        savePlaylist(avgRank, durationToSet, playlist);
    }


    public Genre findGenreByName(String name){
      return   genreRepository.findByName(name);
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


}
