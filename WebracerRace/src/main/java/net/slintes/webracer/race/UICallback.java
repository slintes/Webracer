package net.slintes.webracer.race;

/**
 * callback for communication from race to the ui
 */
public interface UICallback {

    /**
     * start the race
     */
    public void start();

    /**
     * add a car to the track
     *
     * @param car the car
     */
    public void addCar(Car car);

    /**
     * add a car to the track of a specific client
     *
     * @param clientId the id of the client where the car should be added
     * @param car the car
     */
    public void addCar(String clientId, Car car);

    /**
     * removes the car with the given clientId from the track
     *
     * @param clientId the id of the car which should be removed
     */
    public void removeCar(String clientId);

    /**
     * update car
     *
     * @param car the car which is updated
     */
    public void updateCar(Car car);

    /**
     * show a custom message to all clients
     * @param message the message to show
     */
    public void showMessage(String message);

    /**
     * reset clients (after race finished or timed out)
     */
    public void reset();
}


