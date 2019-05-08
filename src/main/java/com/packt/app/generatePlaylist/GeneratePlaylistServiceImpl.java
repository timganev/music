package com.packt.app.generatePlaylist;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.app.Application;
import com.packt.app.artist.Artist;
import com.packt.app.genre.Genre;
import com.packt.app.genre.GenreRepository;
import com.packt.app.playlist.*;
import com.packt.app.track.Track;
import com.packt.app.track.TrackRepository;
import com.packt.app.user.User;
import com.packt.app.user.UserRepository;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
        List<Track> tracksToAdd = new ArrayList<>();


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

            while (playlistCredentials.get(i).getDuration() > durationToSet) {
                Random rand = new Random();
                Track randomTrack = allNeededtracks.get(rand.nextInt(allNeededtracks.size()));

               tracksToAdd.add(randomTrack);
                avgRank += randomTrack.getRank();
                durationToSet += randomTrack.getDuration();
                allNeededtracks.remove(randomTrack);
            }
            i++;
        }
         avgRank = avgRank / tracksToAdd.size();
        savePlaylist(avgRank, durationToSet, playlist);
        Playlist playlist1 = playlistRepository.findFirstByTitle(playlist.getTitle());
        save(tracksToAdd, playlist1);

    }

    public void generatePlaylistWithCriteria(String title, String userName, List<PlaylistCredentials> playlistCredentials,
                                             boolean isSameArtistAllow, boolean isTopRankAllow, int playlistCredentialsSize) throws SQLException {
        int i = 0;
        double durationToSet = 0;
        List<Track> allNeededtracks = new ArrayList<>();
        int avgRank = 0;
        List<Track> tracksToAdd = new ArrayList<>();

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
                while (playlistCredentials.get(i).getDuration() > durationToSet) {
                    Random rand = new Random();
                    Track randomTrack = allNeededtracks.get(rand.nextInt(allNeededtracks.size()));

                    tracksToAdd.add(randomTrack);
                    avgRank += randomTrack.getRank();
                    durationToSet += randomTrack.getDuration();
                }
                i++;
            }
            avgRank = avgRank / tracksToAdd.size();
            savePlaylist(avgRank, durationToSet, playlist);
            Playlist playlist1 = playlistRepository.findFirstByTitle(playlist.getTitle());
            save(tracksToAdd, playlist1);

        } else if (isTopRankAllow) {
            while (i < playlistCredentialsSize) {

                if (playlistCredentials.get(0).getGenre().equals("all")) {
                    allNeededtracks = getAllTracksGroupByArtistOrderByRankDesc();

                } else {
                    String genre = playlistCredentials.get(i).getGenre();
                    Genre genre1 = genreRepository.findByName(genre);
                    allNeededtracks = getAllTracksByGenreGroupByArtistOrderByRankDesc(genre1.getId());

                }

                while (playlistCredentials.get(i).getDuration() > durationToSet) {
                    Track randomTrack = allNeededtracks.get(0);

                    tracksToAdd.add(randomTrack);
                    avgRank += randomTrack.getRank();
                    durationToSet += randomTrack.getDuration();
                    allNeededtracks.remove(randomTrack);
                }
                i++;
            }
            avgRank = avgRank / tracksToAdd.size();
            savePlaylist(avgRank, durationToSet, playlist);
            Playlist playlist1 = playlistRepository.findFirstByTitle(playlist.getTitle());
            save(tracksToAdd, playlist1);

        } else {
            while (i < playlistCredentialsSize) {

                if (playlistCredentials.get(0).getGenre().equals("all")) {
                    allNeededtracks = getAllTracksOrderByRankDesc();


                } else {
                    String genre = playlistCredentials.get(i).getGenre();
                    Genre genre1 = genreRepository.findByName(genre);
                    allNeededtracks = getAllTracksByGenreOrderByRankDesc(genre1.getId());

                }

                while (playlistCredentials.get(i).getDuration() > durationToSet) {
                    Random rand = new Random();
                    Track randomTrack = allNeededtracks.get(rand.nextInt(allNeededtracks.size()));


                    tracksToAdd.add(randomTrack);
                    avgRank += randomTrack.getRank();
                    durationToSet += randomTrack.getDuration();
                }
                i++;
            }

            avgRank = avgRank / tracksToAdd.size();
            savePlaylist(avgRank, durationToSet, playlist);
            Playlist playlist1 = playlistRepository.findFirstByTitle(playlist.getTitle());
            save(tracksToAdd, playlist1);
        }
    }


//    public List<Track> sortList(List<Track> trackList) {
//        trackList.sort(new ComparatorClass());
//        return trackList;
//    }


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


    public void save(List<Track> tracks, Playlist playlist) throws SQLException {
        Properties dbprops = new Properties();
        dbprops.put("javax.persistence.jdbc.user", "root");
        dbprops.put("javax.persistence.jdbc.password", "firstreactiveapp");
        int playlistId = playlist.getId();

        try (

                Connection connection = DriverManager.getConnection
                        ("jdbc:mariadb://reactive.ccae2duiwmn3.us-east-2.rds.amazonaws.com/root?" +
                                "user=root&password=firstreactiveapp");
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT into playlists_tracks (playlist_id, track_id) values (?,?)")

        ) {
            int i = 0;

            for (Track track : tracks) {
                statement.setObject(1, playlistId);
                statement.setObject(2, track.getId());
                statement.addBatch();
                i++;
                if (i % 100 == 0 || i == tracks.size()) {
                    statement.executeBatch(); // Execute every 100 items.
                }

            }
        }
    }

}
