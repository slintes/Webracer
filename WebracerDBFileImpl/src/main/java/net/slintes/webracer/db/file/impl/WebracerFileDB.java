package net.slintes.webracer.db.file.impl;

import net.slintes.webracer.db.Car;
import net.slintes.webracer.db.Race;
import net.slintes.webracer.db.WebracerDB;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * file based implementation of the webracer DB
 */
public class WebracerFileDB implements WebracerDB {

    private static final String FILENAME = "WebracerResults.txt";
    private DateFormat dateFormat;

    public WebracerFileDB() {
        dateFormat = SimpleDateFormat.getDateTimeInstance();
    }

    @Override
    public void saveRaceResults(Race race) {
        try(FileWriter fileWriter = new FileWriter(FILENAME, true)) {

            // write race time
            String startTimeString = getStartTimeString(race.getStartTime());
            fileWriter.append(startTimeString);

            // write cars
            List<Car> cars = race.getCars();
            cars.stream()
                    .sorted((c1, c2) -> new Long(c1.getResultPosition()).compareTo(new Long(c2.getResultPosition())))
                    .forEach(c -> { try { fileWriter.append(getCarString(c)); }
                                    catch (IOException e) { throw new UncheckedIOException(e); }}
                    ); // checked exceptions in lambdas are ugly... :/

            fileWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCarString(Car c) {
        String carString = "";
        boolean finished = c.getResultPosition() > 0;
        carString += "  " + (finished ? c.getResultPosition() : "Out of race")
                + ": " + c.getName()
                + (finished ? " (" + getTimeString(c.getTime()) + ")\n" : "\n");
        return carString;
    }

    private String getStartTimeString(long timeInMS) {
        String formatted = dateFormat.format(new Date(timeInMS)) + "\n";
        return formatted;
    }

    private String getTimeString(long resultInMs) {
        long h = resultInMs / 100 % 10;
        long s = (resultInMs / 1000) % 60;
        long m = (resultInMs / (60 * 1000)) % 60;
        return String.format("%d:%02d,%d", m, s, h);
    }


}
