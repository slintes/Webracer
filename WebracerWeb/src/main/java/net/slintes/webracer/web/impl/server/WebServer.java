package net.slintes.webracer.web.impl.server;

import net.slintes.webracer.race.Car;
import net.slintes.webracer.race.Race;
import net.slintes.webracer.race.UICallback;
import net.slintes.webracer.web.impl.server.commands.server.ServerCommand;
import net.slintes.webracer.web.impl.server.commands.server.impl.ServerMessageCommand;
import net.slintes.webracer.web.impl.server.netty.WebServerStarter;
import net.slintes.webracer.web.impl.server.netty.WebSocketAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public class WebServer implements UICallback {

    private final Race race;

    // TODO is this synchronizing enough?
    private List<WebSocketAdapter> clientSessions = Collections.synchronizedList(new ArrayList<>());

    public WebServer(Race race) {
        this.race = race;
    }

    public void startServer() {
        String track = new TrackConverter().convertToJson(race.getTrack());
        WebServerStarter webServerStarter = new WebServerStarter(this, track);
        webServerStarter.start();
    }

    /* methods called from WebsocketAdapter */

    public void registerClient(WebSocketAdapter wsa) {
        clientSessions.add(wsa);
        race.registerClient(getClientId(wsa));

        // test only!
        race.registerCar(getClientId(wsa), "testname");
    }

    public void unRegisterClient(WebSocketAdapter wsa) {
        clientSessions.remove(wsa);
        race.unRegisterClient(getClientId(wsa));
    }

    public void onMessage(String message) {
        // TODO
    }

    /* UICallback Methods */

    @Override
    public void start() {

    }

    @Override
    public void addCar(Car car) {

    }

    @Override
    public void updateCar(Car car) {

    }

    @Override
    public void showMessage(String message) {
        sendCommand(new ServerMessageCommand(message));
    }

    @Override
    public void showResults() {

    }

    @Override
    public void reset() {

    }


    /* private methods */

    private void sendCommand(ServerCommand command) {
        clientSessions.stream().forEach(wsa -> sendCommand(wsa, command));
    }

    private void sendCommand(String clientId, ServerCommand command) {
        // find websocket session
        Optional<WebSocketAdapter> wsaOptional = clientSessions.stream().
                filter(wsa -> clientId.equals(getClientId(wsa))).
                findFirst();

        if (wsaOptional.isPresent()) {
            sendCommand(wsaOptional.get(), command);
        } else {
            System.out.println("client not found: " + clientId);
        }

    }

    private void sendCommand(WebSocketAdapter wsa, ServerCommand command) {
        String json = command.getJson();
        System.out.println("message to client " + getClientId(wsa) + ": " + json);
        try {
            // send message
            wsa.getRemote().sendString(json);
        } catch (IOException e) {
            System.out.println("error sending message" + e.getMessage());
        }
    }

    private String getClientId(WebSocketAdapter wsa) {
        return "" + wsa.toString();
    }

}
