package com.packt.app.track;

import java.util.List;

public interface TrackService {
    Iterable<Track> getTracks();
    void saveTrack(Track track);


}
