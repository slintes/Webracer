package net.slintes.webracer.race.impl;

import net.slintes.webracer.car.CarFactory;
import net.slintes.webracer.race.Race;
import net.slintes.webracer.race.UICallback;
import net.slintes.webracer.track.Track;

/**
 *
 */
public class RaceImpl implements Race {

    private final CarFactory carFactory;
    private final Track track;
    private UICallback uiCallback;

    public RaceImpl(CarFactory carFactory, Track track){
        System.out.println("new Race");
        this.carFactory = carFactory;
        this.track = track;
    }

    @Override
    public void setUICallback(UICallback uiCallback) {
        this.uiCallback = uiCallback;
    }

    @Override
    public String getTrack() {
        return track.getTrack();
    }

    @Override
    public void registerCar(String id) {
        carFactory.newCar();
        uiCallback.sendMessage(id, "race created new car :)");
    }

    @Override
    public void unRegisterCar(String id) {
        // TODO
    }

}
