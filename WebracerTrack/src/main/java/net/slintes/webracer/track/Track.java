package net.slintes.webracer.track;

/**
 * Represents a racing track
 */
public interface Track {

    /**
     * Returns a String representing a track.
     *
     * Each characters in the string represents a tile. They have these meanings:
     *
     * Space = Aspahalt
     * # = Wall
     * . = Grass
     * f = Start/Finish line
     * 1 = Sector 1 line
     * 2 = Sector 2 line
     * A-Z = Start positions
     *
     * @return the string representing a track
     */
    public String getTrack();

}
