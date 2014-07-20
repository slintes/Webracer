package net.slintes.webracer.web;

import net.slintes.webracer.race.RaceCallback;

/**
 *
 */
public interface Web {

    public void start();
    public void registerRaceCallback(RaceCallback raceCallback);
    public void sendMessage(String clienID, String message);
}
