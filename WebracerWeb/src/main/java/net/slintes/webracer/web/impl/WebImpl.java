package net.slintes.webracer.web.impl;

import net.slintes.webracer.race.Car;
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
        webServer = new WebServer(this);
        webServer.start();

        race.setUICallback(new WebUICallback());
    }

    public void registerCar(String id){
        race.registerClient(id);
        race.registerCar(id, "testname");
    }

    public void unRegisterCar(String id) {
        race.unRegisterClient(id);
    }

    public String getTrack() {
        return new TrackConverter().convertToJson(race.getTrack());
    }

    class WebUICallback implements UICallback {
        @Override
        public void start() {

        }

        @Override
        public void addCar(Car car) {

        }

        @Override
        public void updateCar(Car car) {

        }

        @Override
        public void showMessage(String s) {
            webServer.sendMessage(s);
        }

        @Override
        public void showResults() {

        }

        @Override
        public void reset() {

        }
    }

}
