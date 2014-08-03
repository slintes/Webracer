package net.slintes.webracer.web.impl.server.commands.server.impl;

import net.slintes.webracer.web.impl.server.commands.server.AbstractServerCommand;

import java.util.Map;

/**
 * server side websocket command for removing a car
 */
public class ServerRemoveCarCommand extends AbstractServerCommand {

    public ServerRemoveCarCommand(String clientId) {

        super("removeCar");

        Map<String, Object> map = getEmptyMap();
        map.put("clientId", clientId);
        setData(map);

    }

}
