package net.slintes.webracer.race;

/**
 *
 */
public interface RaceCallback {

    public void registerCar(String Id);
    public void unRegisterCar(String Id);
    public void executeCarCommand(CarCommand command);

}


