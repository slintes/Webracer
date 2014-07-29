package net.slintes.webracer.web.impl.server.commands.server.impl;

import net.slintes.webracer.race.Car;
import net.slintes.webracer.web.impl.server.commands.server.AbstractServerCommand;

import java.util.Arrays;
import java.util.Map;

/**
 * server side websocket command for sending a message to all clients
 */
public class ServerAddCarCommand extends AbstractServerCommand {

    public ServerAddCarCommand(String track, Car car) {

        super("addCar");

        Map<String, Object> map = getEmptyMap();
        map.put("startPositionX", getStartXPosition(track, car.getStartPosition()));
        map.put("startPositionY", getStartYPosition(track, car.getStartPosition()));
        map.put("name", car.getName());
        map.put("clientId", car.getClientId());
        setData(map);

    }

    private int getStartXPosition(String track, int startPos){
        String[] rows = track.split("\n");
        String startPosChar = getStartPositionChar(startPos);
        String startRow = Arrays.stream(rows).filter(row -> row.contains(startPosChar)).findFirst().get();
        return startRow.indexOf(startPosChar);
    }

    private int getStartYPosition(String track, int startPos) {
        String[] rows = track.split("\n");
        String startPosChar = getStartPositionChar(startPos);
        for(int i=0; i<rows.length; i++){
            if(rows[i].contains(startPosChar)){
                return i;
            }
        }
        return 0;
    }

    private String getStartPositionChar(int startPosition){
        Character character = new Character((char) ('A' + startPosition - 1));
        return character.toString();
    }
}
