package net.slintes.webracer.web.impl.server.commands.client.impl;

import net.slintes.webracer.web.impl.server.commands.client.AbstractClientCommand;
import net.slintes.webracer.web.impl.server.commands.client.ClientCommandType;

/**
 * register car client command
 */
public class ClientRegisterCarCommand extends AbstractClientCommand {

    private String name;

    public ClientRegisterCarCommand(ClientCommandType type, String name) {
        super(type);
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
