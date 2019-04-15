package com.packt.app.track;

public interface TrackService {
    Iterable<Track> getTracks();
    void saveTrack(Track track);
}
