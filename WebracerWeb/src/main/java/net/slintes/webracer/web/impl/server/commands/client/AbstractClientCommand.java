package net.slintes.webracer.web.impl.server.commands.client;

/**
 * base class for client commands
 */
public class AbstractClientCommand implements ClientCommand {

    private ClientCommandType type;

    public AbstractClientCommand(ClientCommandType type) {
        this.type = type;
    }

    @Override
    public ClientCommandType getType() {
        return type;
    }
}
