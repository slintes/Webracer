package net.slintes.webracer.race;

/**
 * represents a car
 */
public interface Car {

    /**
     * get the starting grid position, can be used as id during the race
     *
     * @return the starting position, starting from 1
     */
    public int getStartPosition();

    /**
     * get the x position for the next round
     *
     * @return x position
     */
    public int getXPosition();

    /**
     * get the y position for the next round
     *
     * @return y position
     */
    public int getYPosition();

    /**
     * get the speed for the next round
     *
     * @return speed
     */
    public int getSpeed();

    /**
     * get the angle for the next round
     *
     * @return angle
     */
    public int getAngle();

    /**
     * check if car has crashed
     *
     * @return true if car has crashed
     */
    public boolean isCrashed();

    /**
     * check if car has finished
     *
     * @return true if car has finished
     */
    public boolean isFinished();

    /**
     * the car's result position
     *
     * @return result position
     */
    public int getResultPosition();

    /**
     * the car's result time in ms
     *
     * @return result time (ms)
     */
    public long getResultTime();

    /**
     * driver's name
     *
     * @return the name
     */
    public String getName();

    /**
     * client id of the owner the car
     *
     * @return the client id
     */
    public String getClientId();

}
