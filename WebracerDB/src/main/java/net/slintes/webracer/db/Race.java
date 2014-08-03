package net.slintes.webracer.db;

import java.util.List;

/**
 * Represents a race
 */
public interface Race {

    /**
     * Get the time when the race started, as unix timestamp
     *
     * @return the start time
     */
    public long getStartTime();

    /**
     * Get the cars that participated at the race, in no special order
     *
     * @return the cars of the race
     */
    public List<Car> getCars();

}
