package net.slintes.webracer.race.impl;

import net.slintes.webracer.car.CarFactory;
import net.slintes.webracer.race.CarCommand;
import net.slintes.webracer.race.Race;
import net.slintes.webracer.race.RaceCallback;
import net.slintes.webracer.web.Web;

/**
 *
 */
public class RaceImpl implements Race {

    private final CarFactory carFactory;
    private final Web web;

    public RaceImpl(CarFactory carFactory, Web web){
        System.out.println("new Race");

        this.carFactory = carFactory;
        this.web = web;

        start();
    }

    private void start() {
        web.registerRaceCallback(new RaceCallbackImpl());
        web.start();
    }

    class RaceCallbackImpl implements RaceCallback {
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
        public void executeCarCommand(CarCommand command) {
            // TODO
        }
    }

}
