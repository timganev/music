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
import java.util.ArrayList;
import java.util.List;

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
    public void generatePlaylist(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PlaylistCredentialsList playlistCredentialsList = generate(req, res);
        String title = playlistCredentialsList.getTitle();
        String userName = playlistCredentialsList.getUsername();
        boolean isSameArtistAllow = playlistCredentialsList.isSameartist();
        boolean isTopRankAllow = playlistCredentialsList.isTopranks();
        List<PlaylistCredentials> playlistCredentials = playlistCredentialsList.getPlaylistCredentials();

        if (playlistCredentials.size() == 1 && playlistCredentials.get(0).getGenre() == null ) {
           saveGeneratedPlaylistWithoutGenre(title, userName, playlistCredentials, isSameArtistAllow, isTopRankAllow);

        } else if (playlistCredentials.size() == 1 && playlistCredentials.get(0).getGenre() != null ) {
            saveGeneratedPlaylistByOneGenre(title, userName, playlistCredentials, isSameArtistAllow, isTopRankAllow);

        }else if (playlistCredentials.size() >1 ){
            saveGeneratedPlaylistByMoreGenre(title, userName, playlistCredentials, isSameArtistAllow, isTopRankAllow);

        }

    }



    @Override
    public void saveGeneratedPlaylistByOneGenre(String title, String userName,
                                                List<PlaylistCredentials> playlistCredentials, boolean isSameArtistAllow1,
                                                boolean isTopRankAllow) {
        Playlist playlist = generatePlaylistByOneGenre(title, userName, playlistCredentials, isSameArtistAllow1, isTopRankAllow);
        playlistRepository.save(playlist);
        String message = String.format(CREATE_PLAYLIST_MESSAGE, playlist.getTitle());
        logger.debug(message);
    }

    @Override
    public void saveGeneratedPlaylistByMoreGenre(String title, String userName,
                                                 List<PlaylistCredentials> playlistCredentials, boolean isSameArtistAllow1,
                                                 boolean isTopRankAllow) {
        Playlist playlist = generatePlaylistByMoreGenres(title, userName,playlistCredentials,isSameArtistAllow1,isTopRankAllow);
        playlistRepository.save(playlist);
        String message = String.format(CREATE_PLAYLIST_MESSAGE, playlist.getTitle());
        logger.debug(message);
    }

    @Override
    public void saveGeneratedPlaylistWithoutGenre(String title, String userName,
                                                  List<PlaylistCredentials> playlistCredentials, boolean isSameArtistAllow,
                                                  boolean isTopRankAllow) {
        Playlist playlist = generatePlaylistWithoutGenre(title, userName,playlistCredentials,isSameArtistAllow,isTopRankAllow);
        playlistRepository.save(playlist);
        String message = String.format(CREATE_PLAYLIST_MESSAGE, playlist.getTitle());
        logger.debug(message);
    }



    public Playlist generatePlaylistByOneGenre(String title, String userName,
                                               List<PlaylistCredentials> playlistCredentials, boolean isSameArtistAllow,
                                               boolean isTopRankAllow) {

        double currentDuration = 0;
        User user = userRepository.findFirstByUsername(userName);
        Playlist playlist = new Playlist(title, user, 0,0);

        int countOfRandomReturns = 0;
        PlaylistCredentials pl = playlistCredentials.get(0);
        String genre = pl.getGenre();

        if (playlistRepository.findFirstByTitle(title) != null) {
            String message = String.format(THROW_WHEN_PLAYLIST_WITH_TITLE_ALREADY_EXIST_MESSAGE, title);
            logger.error(message);
            throw new IllegalArgumentException("Playlist with this title already exist");

        }
        currentDuration = getCurrentDuration(currentDuration, playlist, countOfRandomReturns, pl, genre,isSameArtistAllow,isTopRankAllow);

        if (currentDuration == 0) {
            String message = String.format("No tracks matched in genre %s with duration", pl.getGenre(), pl.getDuration());
            logger.debug(message);

        }
        playlist.setDuration(currentDuration);


        playlist.setUsername(userName);
        playlist.setImage_url("https://vignette.wikia.nocookie.net/uncyclopedia/images/5/56/Music-notes.jpg/revision/latest?cb=20080914120706");

        return playlist;

    }


    public Playlist generatePlaylistByMoreGenres(String title, String userName,
                                                 List<PlaylistCredentials> playlistCredentials, boolean isSameArtistAllow,
                                                 boolean isTopRankAllow){

        double currentDuration = 0;
        User user = userRepository.findFirstByUsername(userName);

        Playlist playlist = new Playlist(title, user, 0,0);

        int currentCredential = 0;
        int countOfRandomReturns = 0;
        int durationToSet = 0;

        if (playlistRepository.findFirstByTitle(title) != null) {
            String message = String.format(THROW_WHEN_PLAYLIST_WITH_TITLE_ALREADY_EXIST_MESSAGE, title);
            logger.error(message);
            throw new IllegalArgumentException("Playlist with this title already exist");

        }

        while (currentCredential < playlistCredentials.size()) {
            PlaylistCredentials pl = playlistCredentials.get(currentCredential);
            String genre = pl.getGenre();

            currentDuration = getCurrentDuration(currentDuration, playlist,
                    countOfRandomReturns, pl, genre,isSameArtistAllow,isTopRankAllow);

            if (currentDuration == 0) {
                String message = String.format(NO_TRACKS_WITH_THIS_DURATION_AND_GENRE, pl.getGenre(), pl.getDuration());
                logger.error(message);
                currentCredential++;
                continue;
            }
            durationToSet += currentDuration;
            currentDuration = 0;
            playlist.setDuration(durationToSet);
            currentCredential++;
        }

        playlist.setUsername(userName);
        playlist.setImage_url("https://vignette.wikia.nocookie.net/uncyclopedia/images/5/56/Music-notes.jpg/revision/latest?cb=20080914120706");
        return playlist;


    }


    public double getCurrentDuration(double currentDuration, Playlist playlist, int countOfRandomReturns,
                                     PlaylistCredentials pl, String genre,boolean isSameArtistAllow,boolean isTopRankAllow) {
        Genre genre1 = genreRepository.findByName(genre);
        List<Track> tracks=new ArrayList<>();
        int indexToIter=0;
        int averageRank=0;
        int currentRank=0;

        if (isTopRankAllow){
             tracks=getTracksByGenreOrderedByRankDesc(genre);
        }
        while (currentDuration < pl.getDuration() + 80) {

            if (countOfRandomReturns > 10) {
                break;
            }
            Track track;

            if (!isTopRankAllow) {
                if ("all".equals(genre)) {
                    track = trackRepository.getRandomTrackFromDB();
                } else {
                    track = trackRepository.getRandomTrackFromDbByGenre(genre1.getId());
                }
                if (track.getDuration() > pl.getDuration() + 80) {
                   break;
                }
            }else {
                track=tracks.get(indexToIter);
                indexToIter++;
            }
        countOfRandomReturns=0;
            if (playlist.getPlaylistTracks() != null && playlist.getPlaylistTracks().contains(track)) {
                while (playlist.getPlaylistTracks().contains(track)) {
                    track = trackRepository.getRandomTrackFromDbByGenre(genre1.getId());
                    countOfRandomReturns++;
                    if (countOfRandomReturns > 5) {
                        break;
                    }
                }
            }

            if (!isSameArtistAllow) {
                if (playlist.getPlaylistArtists() != null && playlist.getPlaylistArtists().contains(track.getArtist())) {
                    continue;
                }
            }
            playlist.getPlaylistTracks().add(track);
            playlist.getPlaylistArtists().add(track.getArtist());
            playlist.getPlaylistGenres().add(track.getGenre());
            currentRank+= track.getRank();
            currentDuration += track.getDuration();

        }
        if (playlist.getPlaylistTracks().isEmpty()) {
            String message = String.format(NO_TRACKS_WITH_THIS_DURATION_AND_GENRE, pl.getGenre(), pl.getDuration());
            logger.error(message);
            throw new NullPointerException(NO_TRACKS_WITH_THIS_DURATION_AND_GENRE);
        }
        averageRank=currentRank/playlist.getPlaylistTracks().size();
        playlist.setAvgrank(averageRank);
        return currentDuration;
    }

    public Playlist generatePlaylistWithoutGenre(String title, String userName,
                                                 List<PlaylistCredentials> playlistCredentials, boolean isSameArtistAllow,
                                                 boolean isTopRankAllow) {
        PlaylistCredentials pl = playlistCredentials.get(0);

        double currentDuration = 0;
        int countOfRandomReturns = 0;
        int durationToSet = 0;
        int averageRank=0;
        int currentRank=0;

        User user = userRepository.findFirstByUsername(userName);

        Playlist playlist = new Playlist(title, user, 0,0);
        Track track = new Track();

        track = trackRepository.getRandomTrackFromDB();

        while (currentDuration < pl.getDuration() + 80) {
            if (track.getDuration() > pl.getDuration() + 80) {
                break;
            }

            if (playlist.getPlaylistTracks() != null && playlist.getPlaylistTracks().contains(track)) {
                while (playlist.getPlaylistTracks().contains(track)) {
                    track = trackRepository.getRandomTrackFromDB();
                    countOfRandomReturns++;
                    if (countOfRandomReturns > 5) {
                        break;
                    }
                }
            }

            if (playlist.getPlaylistTracks() != null && playlist.getPlaylistTracks().contains(track)) {
                countOfRandomReturns++;
                continue;
            }
            if (!isSameArtistAllow) {
                if (playlist.getPlaylistArtists() != null && playlist.getPlaylistArtists().contains(track.getArtist())) {
                    continue;
                }
            }
            playlist.getPlaylistTracks().add(track);
            playlist.getPlaylistArtists().add(track.getArtist());
            playlist.getPlaylistGenres().add(track.getGenre());
            currentRank+= track.getRank();
            currentDuration+=track.getDuration();
        }

        if (currentDuration == 0) {
            String message = String.format("No tracks matched in genre %s with duration", pl.getGenre(), pl.getDuration());
            logger.debug(message);
            throw new NullPointerException();
        }


        if (playlistRepository.findFirstByTitle(title) != null) {
            String message = String.format(THROW_WHEN_PLAYLIST_WITH_TITLE_ALREADY_EXIST_MESSAGE, title);
            logger.error(message);
            throw new IllegalArgumentException(THROW_WHEN_PLAYLIST_WITH_TITLE_ALREADY_EXIST_MESSAGE);
        }

        if (playlist.getPlaylistTracks().isEmpty()) {
            String message = String.format(NO_TRACKS_WITH_THIS_DURATION_AND_GENRE, pl.getGenre(), pl.getDuration());
            logger.error(message);
            throw new NullPointerException(NO_TRACKS_WITH_THIS_DURATION_AND_GENRE);
        }

        averageRank=currentRank/playlist.getPlaylistTracks().size();
        playlist.setAvgrank(averageRank);
        durationToSet += currentDuration;
        playlist.setDuration(durationToSet);
        playlist.setUsername(userName);
        playlist.setImage_url("https://vignette.wikia.nocookie.net/uncyclopedia/images/5/56/Music-notes.jpg/revision/latest?cb=20080914120706");
        return playlist;

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
