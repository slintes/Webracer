package net.slintes.webracer.db;

/**
 * A database which can save race results
 */
public interface WebracerDB {

    /**
     * Save the result of a race
     *
     * @param race the race to save
     */
    public void saveRaceResults(Race race);

}
