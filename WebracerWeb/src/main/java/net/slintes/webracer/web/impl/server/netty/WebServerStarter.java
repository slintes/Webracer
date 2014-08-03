package net.slintes.webracer.web.impl.server.netty;

import net.slintes.webracer.web.impl.server.WebServer;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * This class is able to start a jetty webserver and registers handlers for static content and websockets
 */
public class WebServerStarter {

    private final WebServer webServer;
    private final String track;

    public WebServerStarter(WebServer webServer, String track) {
        this.webServer = webServer;
        this.track = track;
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
        TrackHandler trackHandler = new TrackHandler(track);
        ContextHandler trackContext = new ContextHandler();
        trackContext.setContextPath("/track/");
        trackContext.setHandler(trackHandler);

        // create websocket handler
        WebSocketHandler wsHandler = new WebSocketHandler() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.getPolicy().setIdleTimeout(60 * 60 * 1000);
                factory.setCreator(
                        (servletUpgradeRequest, servletUpgradeResponse)
                                -> new WebSocketAdapter(webServer));
            }
        };
        ContextHandler websocketContext = new ContextHandler();
        websocketContext.setContextPath("/ws");
        websocketContext.setHandler(wsHandler);

        // add resource handlers to the server.
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{trackContext, resourceContext, websocketContext, new DefaultHandler()});
        server.setHandler(handlers);

        // start jetty
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
