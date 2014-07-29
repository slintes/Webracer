package net.slintes.webracer.web.impl.server.commands.client.impl;

import net.slintes.webracer.web.impl.server.commands.client.AbstractClientCommand;
import net.slintes.webracer.web.impl.server.commands.client.ClientCommandType;

/**
 * register client client command
 */
public class ClientRegisterClientCommand extends AbstractClientCommand {

    private String clientId;

    public ClientRegisterClientCommand(ClientCommandType type, String clientId) {
        super(type);
        this.clientId = clientId;
    }

    public String getClientId(){
        return clientId;
    }
}
