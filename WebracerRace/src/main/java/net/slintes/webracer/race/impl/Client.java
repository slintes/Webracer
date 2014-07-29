package net.slintes.webracer.race.impl;

import net.slintes.webracer.race.Car;

/**
 * car implementation
 */
public class Client implements Car {

    private int startPosition = 0;
    private int xPos = 0;
    private int yPos = 0;
    private int speed = 0;
    private int angle = 90;
    private boolean isCrashed = false;
    private boolean isFinished = false;
    private String name = null;
    private String clientId = null;

    public Client(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public int getStartPosition() {
        return startPosition;
    }

    @Override
    public int getXPosition() {
        return xPos;
    }

    @Override
    public int getYPosition() {
        return yPos;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getAngle() {
        return angle;
    }

    @Override
    public boolean isCrashed() {
        return isCrashed;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getClientId() {
        return clientId;
    }


    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setCrashed(boolean isCrashed) {
        this.isCrashed = isCrashed;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public void setName(String name) {
        this.name = name;
    }

}
