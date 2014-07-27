package net.slintes.webracer.web.impl.server.netty;

import net.slintes.webracer.web.impl.server.WebServer;
import org.eclipse.jetty.websocket.api.Session;

/**
 * websocket adapter
 */
public class WebSocketAdapter extends org.eclipse.jetty.websocket.api.WebSocketAdapter {

    private final WebServer webServer;

    public WebSocketAdapter(WebServer web){
        this.webServer = web;
    };

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        System.out.println("websocket close");

        getSession().close();
        webServer.unRegisterClient(this);

        super.onWebSocketClose(statusCode, reason);
    }

    @Override
    public void onWebSocketConnect(Session session) {
        super.onWebSocketConnect(session);

        System.out.println("websocket connect");
        webServer.registerClient(this);

    }

    @Override
    public void onWebSocketError(Throwable cause) {

        System.out.println("websocket error: " + cause.getMessage());
        webServer.unRegisterClient(this);

        super.onWebSocketError(cause);
    }

    @Override
    public void onWebSocketText(String message) {
        if (isNotConnected()) {
            return;
        }

        webServer.onMessage(message);

        System.out.println("websocket message: " + message);
        super.onWebSocketText(message);
    }

}
