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
     * driver's name
     *
     * @return the name
     */
    public String getName();
}
