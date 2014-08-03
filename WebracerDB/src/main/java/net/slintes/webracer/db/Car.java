package net.slintes.webracer.db;

/**
 * Represents a car in a race
 */
public interface Car {

    /**
     * Get the name of the player of this car
     *
     * @return player's name
     */
    public String getName();

    /**
     * Get the position of the car at the end of the race
     *
     * @return the result position
     */
    public int getResultPosition();

    /**
     * Get the time of the car, in milliseconds
     *
     * @return time in milliseconds
     */
    public long getTime();

}
