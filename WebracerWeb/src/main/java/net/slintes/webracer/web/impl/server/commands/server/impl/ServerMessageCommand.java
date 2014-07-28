package net.slintes.webracer.web.impl.server.commands.server.impl;

import net.slintes.webracer.web.impl.server.commands.server.AbstractServerCommand;

import java.util.Map;

/**
 * server side websocket command for sending a message to all clients
 */
public class ServerMessageCommand extends AbstractServerCommand {

    public ServerMessageCommand(String message) {

        super("message");

        Map<String, Object> map = getEmptyMap();
        map.put("message", message);
        setData(map);

    }
}
