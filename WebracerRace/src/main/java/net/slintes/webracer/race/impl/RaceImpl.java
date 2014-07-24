package net.slintes.webracer.race.impl;

import net.slintes.webracer.car.CarFactory;
import net.slintes.webracer.race.Race;
import net.slintes.webracer.race.Web2RaceCallback;
import net.slintes.webracer.track.Track;
import net.slintes.webracer.web.Web;

/**
 *
 */
public class RaceImpl implements Race {

    private final CarFactory carFactory;
    private final Web web;
    private final Track track;

    public RaceImpl(CarFactory carFactory, Web web, Track track){
        System.out.println("new Race");

        this.carFactory = carFactory;
        this.web = web;
        this.track = track;

        start();
    }

    private void start() {
        web.registerRaceCallback(new Web2RaceCallbackImpl());
        web.start();
    }

    class Web2RaceCallbackImpl implements Web2RaceCallback {
        @Override
        public void registerCar(String Id) {
            carFactory.newCar();
            web.sendMessage(Id, "message from race!");
        }

        @Override
        public void unRegisterCar(String Id) {
            // TODO
        }

        @Override
        public String getTrack() {
            return track.getTrack();
        }
    }

}
