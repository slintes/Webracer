package net.slintes.webracer.track.longTrack.impl;

import net.slintes.webracer.track.Track;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 *
 */
public class TrackLong implements Track {

    private static final String fileName = "/track.txt";

    @Override
    public String getTrack() {

        URL resource = this.getClass().getClassLoader().getResource("track.txt");
        StringBuffer track = new StringBuffer();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()));
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                track.append(inputLine).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return track.toString();
    }

}
