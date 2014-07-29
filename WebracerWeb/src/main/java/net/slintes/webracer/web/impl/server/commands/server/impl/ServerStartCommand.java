package net.slintes.webracer.web.impl.server.commands.server.impl;

import net.slintes.webracer.web.impl.server.commands.server.AbstractServerCommand;

/**
 * server side websocket command for sending a message to all clients
 */
public class ServerStartCommand extends AbstractServerCommand {

    public ServerStartCommand() {

        super("start");

    }

}
