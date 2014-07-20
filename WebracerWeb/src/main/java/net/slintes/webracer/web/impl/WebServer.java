package net.slintes.webracer.web.impl;

/**
 *
 */
public class WebServer {

    private final WebImpl web;

    public WebServer(WebImpl web) {
        this.web = web;
    }

    public void start() {

        web.registerClient("testid");

    }

    public void sendMessage(String clientId, String message) {

        System.out.println("message to client " + clientId + ": " + message);

    }

}
