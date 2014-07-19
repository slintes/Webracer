package net.slintes.webracer.car.impl;

import net.slintes.webracer.car.Car;

/**
 *
 */
public class CarImpl implements Car {

    public CarImpl() {
        System.out.println("new Car");
    }

    @Override
    public void accelerate() {
        System.out.println("accelerate");
    }

    @Override
    public void brake() {
        System.out.println("brake");
    }

    @Override
    public void turnLeft() {
        System.out.println("turnLeft");
    }

    @Override
    public void turnRight() {
        System.out.println("turnRight");
    }

    @Override
    public void doNothing() {
        System.out.println("do nothing");
    }
}
