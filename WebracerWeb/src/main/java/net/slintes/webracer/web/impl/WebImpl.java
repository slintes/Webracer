package net.slintes.webracer.web.impl;

import net.slintes.webracer.race.Race;
import net.slintes.webracer.race.UICallback;
import net.slintes.webracer.web.impl.netty.WebServer;

/**
 *
 */
public class WebImpl {

    private final Race race;
    private WebServer webServer;

    public WebImpl(Race race) {
        this.race = race;
    }

    public void start() {

        race.setUICallback(new WebUICallback());

        webServer = new WebServer(this);
        webServer.start();

    }

    public void registerCar(String id){
        race.registerCar(id);
    }

    public void unRegisterCar(String id) {
        race.unRegisterCar(id);
    }

    public String getTrack() {
        return new TrackConverter().convertToJson(race.getTrack());
    }

    class WebUICallback implements UICallback {
        @Override
        public void sendMessage(String carId, String message) {
            webServer.sendMessage(carId, message);
        }
    }

}
