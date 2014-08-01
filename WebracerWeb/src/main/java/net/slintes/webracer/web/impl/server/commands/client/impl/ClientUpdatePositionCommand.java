package net.slintes.webracer.web.impl.server.commands.client.impl;

import net.slintes.webracer.web.impl.server.commands.client.AbstractClientCommand;
import net.slintes.webracer.web.impl.server.commands.client.ClientCommandType;

/**
 * update position client command
 */
public class ClientUpdatePositionCommand extends AbstractClientCommand {

    private final int xPos;
    private final int yPos;
    private final int speed;
    private final int angle;
    private boolean isCrashed;
    private boolean isFinished;


    public ClientUpdatePositionCommand(ClientCommandType type, int xPos, int yPos, int speed, int angle,
                                       boolean isCrashed, boolean isFinished) {
        super(type);
        this.xPos = xPos;
        this.yPos = yPos;
        this.speed = speed;
        this.angle = angle;
        this.isCrashed = isCrashed;
        this.isFinished = isFinished;

    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getSpeed() {
        return speed;
    }

    public int getAngle() {
        return angle;
    }

    public boolean isCrashed() {
        return isCrashed;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
