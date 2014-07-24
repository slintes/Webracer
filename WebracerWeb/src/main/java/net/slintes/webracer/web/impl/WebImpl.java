package net.slintes.webracer.web.impl;

import net.slintes.webracer.race.Web2RaceCallback;
import net.slintes.webracer.web.Web;

/**
 *
 */
public class WebImpl implements Web {

    private WebServer webServer;
    private Web2RaceCallback raceCallback;

    @Override
    public void start() {

        webServer = new WebServer(this);
        webServer.start();

    }

    @Override
    public void registerRaceCallback(Web2RaceCallback raceCallback) {
        this.raceCallback = raceCallback;
    }

    @Override
    public void sendMessage(String clientId, String message) {
        webServer.sendMessage(clientId, message);
    }

    public void registerCar(String id){
        raceCallback.registerCar(id);
    }

    public void unRegisterCar(String id) {
        raceCallback.unRegisterCar(id);
    }

    public String getTrack() {
        return raceCallback.getTrack();
    }
}
