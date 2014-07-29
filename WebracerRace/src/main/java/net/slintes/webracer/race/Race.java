package net.slintes.webracer.race;

/**
 *
 */
public interface Race {

    /**
     * set the ui callback, which is used for communication from race to ui
     *
     * @param uiCallback
     */
    public void setUICallback(UICallback uiCallback);

    /**
     * get the track
     *
     * @return the track
     */
    public String getTrack();

    /**
     * register a new client (no car yet)
     *
     * @param clientId a unique id between clients
     * @return true if client can join the race
     */
    public boolean registerClient(String clientId);

    /**
     * unregister a client, it will not get any updates anymore
     *
     * @param clientId the clients unique id from registerClient()
     */
    public void unRegisterClient(String clientId);

    /**
     * register a new car
     *
     * @param clientId the clients unique id from registerClient()
     * @param name the players name
     *
     * @return the position in the starting grid, starting from 1; 0 if car could not be registered
     */
    public int registerCar(String clientId, String name);

    /**
     * update the position for the next round
     * unit of x and y is not relevant for the race, just be consistent
     * in communication between ui and race
     *
     * @param clientId the clients unique id from registerClient()
     * @param xPos x position
     * @param yPos y position
     * @param angle the angle
     */
    public void nextPosition(String clientId, int xPos, int yPos, int speed, int angle);

    /**
     * the client has crashed at given position
     *
     * @param clientId the clients unique id from registerClient()
     * @param xPos x position
     * @param yPos y position
     * @param angle the angle
     */
    public void crash(String clientId, int xPos, int yPos, int angle);

    /**
     * the client has reached the finish line at given position
     *
     * @param clientId the clients unique id from registerClient()
     * @param xPos x position
     * @param yPos y position
     * @param angle the angle
     */
    public void finish(String clientId, int xPos, int yPos, int angle);

}
