package net.slintes.webracer.db;

import java.util.List;

/**
 * represents a race
 */
public interface Race {

    public long getStartTime();
    public List<Car> getCars();

}
