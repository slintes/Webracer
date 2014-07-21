package net.slintes.webracer.web.impl;

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

        // create websocket handler
        WebSocketHandler wsHandler = new WebSocketHandler()
        {
            @Override
            public void configure(WebSocketServletFactory factory)
            {
//                factory.register(WebWebSocketAdapter.class);
                factory.setCreator(
                        (servletUpgradeRequest, servletUpgradeResponse)
                                -> new WebWebSocketAdapter(WebServer.this));
            }
        };
        ContextHandler websocketContext = new ContextHandler();
        websocketContext.setContextPath("/ws");
        websocketContext.setHandler(wsHandler);

        // add resource handler to the server.
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { resourceContext, websocketContext, new DefaultHandler() });
        server.setHandler(handlers);

        // start jetty
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            // no good...
        }

    }

    public void sendMessage(String clientId, String message) {
        System.out.println("message to client " + clientId + ": " + message);

        // find session
        Optional<WebWebSocketAdapter> wsAdapterOptional = clientSessions.stream().
                filter(wsa -> clientId.equals(getClientId(wsa))).
                findFirst();

        if(wsAdapterOptional.isPresent()){
            try {
                // send message
                wsAdapterOptional.get().getRemote().sendString(message);
            } catch (IOException e) {
                System.out.println("error sending message" + e.getMessage());
            }
        }
        else {
            System.out.println("client not found: " + clientId);
        }

    }

    public void registerClient(WebWebSocketAdapter webWebSocketAdapter) {
        clientSessions.add(webWebSocketAdapter);
        web.registerClient(getClientId(webWebSocketAdapter));
    }

    public void unRegisterClient(WebWebSocketAdapter webWebSocketAdapter) {
        clientSessions.remove(webWebSocketAdapter);
        web.unRegisterClient(getClientId(webWebSocketAdapter));
    }

    public void onMessage(String message) {
        // TODO
    }

    private String getClientId(WebWebSocketAdapter webWebSocketAdapter){
        return "" + webWebSocketAdapter.toString();
    }
}
