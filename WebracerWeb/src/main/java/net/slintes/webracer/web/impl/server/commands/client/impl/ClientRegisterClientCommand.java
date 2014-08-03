package net.slintes.webracer.web.impl.server.commands.client.impl;

import net.slintes.webracer.web.impl.server.commands.client.AbstractClientCommand;
import net.slintes.webracer.web.impl.server.commands.client.ClientCommandType;

/**
 * register client client command
 */
public class ClientRegisterClientCommand extends AbstractClientCommand {

    private String clientId;

    public ClientRegisterClientCommand(String clientId) {
        super(ClientCommandType.RegisterClient);
        this.clientId = clientId;
    }

    public String getClientId(){
        return clientId;
    }
}
