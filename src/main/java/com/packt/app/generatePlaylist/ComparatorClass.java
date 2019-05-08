package com.packt.app.generatePlaylist;

import com.packt.app.track.Track;

import java.util.Comparator;

public class ComparatorClass implements Comparator<Track> {

        @Override
        public int compare(Track a, Track b) {

            return a.getRank() - b.getRank() ;
    }
}
