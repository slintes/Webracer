package net.slintes.webracer.web;

import net.slintes.webracer.race.Web2RaceCallback;

/**
 *
 */
public interface Web {

    public void start();
    public void registerRaceCallback(Web2RaceCallback raceCallback);
    public void sendMessage(String clienID, String message);
}
