package net.slintes.webracer.web.impl.netty;

import net.slintes.webracer.web.impl.WebImpl;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public class WebServer {

    private final WebImpl web;

    // TODO is this synchronizing enough?
    private List<WebWebSocketAdapter> clientSessions = Collections.synchronizedList(new ArrayList<>());

    public WebServer(WebImpl web) {
        this.web = web;
    }

    public void start() {

        // create and configure embedded jetty server
        Server server = new Server(8080);

        // create resource handler for static content
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        String webDir = this.getClass().getClassLoader().getResource("/html").toExternalForm();
        resourceHandler.setResourceBase(webDir);
        ContextHandler resourceContext = new ContextHandler();
        resourceContext.setContextPath("/");
        resourceContext.setHandler(resourceHandler);

        // create resource handler for track
        TrackHandler trackHandler = new TrackHandler(web.getTrack());
        ContextHandler trackContext = new ContextHandler();
        trackContext.setContextPath("/track/");
        trackContext.setHandler(trackHandler);

        // create websocket handler
        WebSocketHandler wsHandler = new WebSocketHandler()
        {
            @Override
            public void configure(WebSocketServletFactory factory)
            {
                factory.setCreator(
                        (servletUpgradeRequest, servletUpgradeResponse)
                                -> new WebWebSocketAdapter(WebServer.this));
            }
        };
        ContextHandler websocketContext = new ContextHandler();
        websocketContext.setContextPath("/ws");
        websocketContext.setHandler(wsHandler);

        // add resource handlers to the server.
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { trackContext, resourceContext, websocketContext, new DefaultHandler() });
        server.setHandler(handlers);

        // start jetty
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(String message){
        clientSessions.stream().forEach(wsa -> sendMessage(wsa, message));
    }

    public void sendMessage(String clientId, String message) {
        // find websocket session
        Optional<WebWebSocketAdapter> wsAdapterOptional = clientSessions.stream().
                filter(wsa -> clientId.equals(getClientId(wsa))).
                findFirst();

        if(wsAdapterOptional.isPresent()){
            sendMessage(wsAdapterOptional.get(), message);
        }
        else {
            System.out.println("client not found: " + clientId);
        }

    }

    private void sendMessage(WebWebSocketAdapter wsa, String message) {
        System.out.println("message to client " + getClientId(wsa) + ": " + message);
        try {
            // send message
            wsa.getRemote().sendString(message);
        } catch (IOException e) {
            System.out.println("error sending message" + e.getMessage());
        }
    }

    public void registerClient(WebWebSocketAdapter webWebSocketAdapter) {
        clientSessions.add(webWebSocketAdapter);
        web.registerCar(getClientId(webWebSocketAdapter));
    }

    public void unRegisterClient(WebWebSocketAdapter webWebSocketAdapter) {
        clientSessions.remove(webWebSocketAdapter);
        web.unRegisterCar(getClientId(webWebSocketAdapter));
    }

    public void onMessage(String message) {
        // TODO
    }

    private String getClientId(WebWebSocketAdapter webWebSocketAdapter){
        return "" + webWebSocketAdapter.toString();
    }
}
