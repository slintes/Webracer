package net.slintes.webracer.web.impl.server.commands.server.impl;

import net.slintes.webracer.web.impl.server.commands.server.AbstractServerCommand;

import java.util.Map;

/**
 * server side remove car command
 */
public class ServerRemoveCarCommand extends AbstractServerCommand {

    public ServerRemoveCarCommand(String clientId) {

        super("removeCar");

        Map<String, Object> map = getEmptyMap();
        map.put("clientId", clientId);
        setData(map);

    }

}
