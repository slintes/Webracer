package net.slintes.webracer.web.impl.server.commands.client;

import net.slintes.webracer.web.impl.server.commands.client.impl.ClientRegisterCarCommand;
import net.slintes.webracer.web.impl.server.commands.client.impl.ClientRegisterClientCommand;
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
            default: return null;
        }

    }

}
