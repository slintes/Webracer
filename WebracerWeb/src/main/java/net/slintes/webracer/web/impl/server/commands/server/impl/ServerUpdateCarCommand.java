package net.slintes.webracer.web.impl.server.commands.server.impl;

import net.slintes.webracer.race.Car;
import net.slintes.webracer.web.impl.server.commands.server.AbstractServerCommand;

import java.util.Map;

/**
 * server command for updating a car
 */
public class ServerUpdateCarCommand extends AbstractServerCommand {

    public ServerUpdateCarCommand(Car car) {

        super("updateCar");

        Map<String, Object> map = getEmptyMap();
        map.put("xPos", car.getXPosition());
        map.put("yPos", car.getYPosition());
        map.put("speed", car.getSpeed());
        map.put("angle", car.getAngle());
        map.put("clientId", car.getClientId());
        setData(map);

    }

}
