package net.slintes.webracer.race.impl;

import net.slintes.webracer.db.Car;
import net.slintes.webracer.db.Race;
import net.slintes.webracer.db.WebracerDB;

import java.util.ArrayList;
import java.util.List;

/**
 * saves the race results using the WebracerDB bundle
 */
public class RaceDB {

    private final WebracerDB webracerDB;

    public RaceDB(WebracerDB webracerDB) {
        this.webracerDB = webracerDB;
    }

    public void saveResults(long raceStartTime, List<Client> cars){

        // quick and dirty solution with anonymous classes...
        List<Car> dbCars = new ArrayList<>();
        cars.stream().forEach(c -> dbCars.add(new Car() {
            @Override
            public String getName() {
                return c.getName();
            }

            @Override
            public int getResultPosition() {
                return c.getResultPosition();
            }

            @Override
            public long getTime() {
                return c.getResultTime();
            }
        }));

        webracerDB.saveRaceResults(new Race() {
            @Override
            public long getStartTime() {
                return raceStartTime;
            }

            @Override
            public List<Car> getCars() {
                return dbCars;
            }
        });

    }
}
