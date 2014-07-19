package net.slintes.webracer.car.impl;

import net.slintes.webracer.car.Car;
import net.slintes.webracer.car.CarFactory;

/**
 *
 */
public class CarFactoryImpl implements CarFactory {

    @Override
    public Car newCar() {
        return new CarImpl();
    }

}
