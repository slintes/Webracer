package net.slintes.webracer.race.impl;

import net.slintes.webracer.car.Car;
import net.slintes.webracer.car.CarFactory;
import net.slintes.webracer.race.Race;

/**
 *
 */
public class RaceImpl implements Race {

    private final CarFactory carFactory;

    public RaceImpl(CarFactory carFactory){
        System.out.println("new Race");
        this.carFactory = carFactory;
        start();
    }

    private void start() {
        Car car = carFactory.newCar();
        car.accelerate();
    }

}
