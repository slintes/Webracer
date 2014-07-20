package net.slintes.webracer.web.impl;

import net.slintes.webracer.race.RaceCallback;
import net.slintes.webracer.web.Web;

/**
 *
 */
public class WebImpl implements Web {

    private WebServer webServer;
    private RaceCallback raceCallback;

    @Override
    public void start() {

        webServer = new WebServer(this);
        webServer.start();

    }

    @Override
    public void registerRaceCallback(RaceCallback raceCallback) {
        this.raceCallback = raceCallback;
    }

    @Override
    public void sendMessage(String clientId, String message) {
        webServer.sendMessage(clientId, message);
    }

    public void registerClient(String id){
        raceCallback.registerCar(id);
    }

    public void unRegisterClient(String id) {
        raceCallback.unRegisterCar(id);
    }


}
