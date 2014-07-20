package net.slintes.webracer.web.impl;

import net.slintes.webracer.race.RaceCallback;
import net.slintes.webracer.web.Web;

/**
 *
 */
public class WebImpl implements Web {

    private WebVerticle webVerticle;
    private RaceCallback raceCallback;

    @Override
    public void start() {

        webVerticle = new WebVerticle(this);
        webVerticle.start();

    }

    @Override
    public void registerRaceCallback(RaceCallback raceCallback) {
        this.raceCallback = raceCallback;
    }

    @Override
    public void sendMessage(String clienId, String message) {
        webVerticle.sendMessage(clienId, message);
    }

    public void registerClient(String clientID){
        raceCallback.registerCar(clientID);
    }
}
