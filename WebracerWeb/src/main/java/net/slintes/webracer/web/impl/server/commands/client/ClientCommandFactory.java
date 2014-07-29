package net.slintes.webracer.web.impl.server.commands.client;

import net.slintes.webracer.web.impl.server.commands.client.impl.ClientRegisterCarCommand;
import net.slintes.webracer.web.impl.server.commands.client.impl.ClientRegisterClientCommand;
import net.slintes.webracer.web.impl.server.commands.client.impl.ClientUpdatePositionCommand;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * client command factory
 */
public class ClientCommandFactory {

    private static final String COMMAND = "command";
    private static final String DATA = "data";

    private static final String COMMAND_REGISTER_CLIENT = "registerClient";
    private static final String COMMAND_REGISTER_CLIENT_CLIENTID = "clientId";

    private static final String COMMAND_REGISTER_CAR = "registerCar";
    private static final String COMMAND_REGISTER_CAR_NAME = "name";

    private static final String COMMAND_UPDATE_POSITION = "updatePosition";
    private static final String COMMAND_UPDATE_POSITION_POS_X = "posX";
    private static final String COMMAND_UPDATE_POSITION_POS_Y = "posY";
    private static final String COMMAND_UPDATE_POSITION_SPEED = "speed";
    private static final String COMMAND_UPDATE_POSITION_ANGLE = "angle";

    private final JSONParser parser;

    public ClientCommandFactory() {
        parser = new JSONParser();
    }

    public ClientCommand getClientCommand(String json){
        JSONObject jsonCommand;
        try {
            jsonCommand = (JSONObject) parser.parse(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        String commandName = jsonCommand.get(COMMAND).toString();
        JSONObject jsonData = (JSONObject) jsonCommand.get(DATA);

        switch (commandName){
            case COMMAND_REGISTER_CLIENT:
                String clientId = jsonData.get(COMMAND_REGISTER_CLIENT_CLIENTID).toString();
                ClientRegisterClientCommand registerClientCommand =
                        new ClientRegisterClientCommand(ClientCommandType.RegisterClient, clientId);
                return registerClientCommand;
            case COMMAND_REGISTER_CAR:
                String name = jsonData.get(COMMAND_REGISTER_CAR_NAME).toString();
                ClientRegisterCarCommand registerCarCommand =
                        new ClientRegisterCarCommand(ClientCommandType.RegisterCar, name);
                return registerCarCommand;
            case COMMAND_UPDATE_POSITION:
                int posX = ((Long) jsonData.get(COMMAND_UPDATE_POSITION_POS_X)).intValue();
                int posY = ((Long) jsonData.get(COMMAND_UPDATE_POSITION_POS_Y)).intValue();
                int speed = ((Long) jsonData.get(COMMAND_UPDATE_POSITION_SPEED)).intValue();
                int angle = ((Long) jsonData.get(COMMAND_UPDATE_POSITION_ANGLE)).intValue();
                ClientUpdatePositionCommand updatePositionCommand =
                        new ClientUpdatePositionCommand(ClientCommandType.UpdatePosition, posX, posY, speed, angle);
                return updatePositionCommand;
            default: return null;
        }

    }

}
