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
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {

        System.out.println("websocket close");

        getSession().close();
        webServer.unRegisterClient(this);

    }

    @Override
    public void onWebSocketConnect(Session session) {

        System.out.println("websocket connect");
        super.onWebSocketConnect(session);

    }

    @Override
    public void onWebSocketError(Throwable cause) {

        System.out.println("websocket error: " + cause.getMessage());
        webServer.unRegisterClient(this);

    }

    @Override
    public void onWebSocketText(String message) {
        if (isNotConnected()) {
            return;
        }

        System.out.println("websocket message: " + message);
        webServer.onMessage(this, message);

    }

}
