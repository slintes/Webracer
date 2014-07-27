package net.slintes.webracer.web.impl;

import net.slintes.webracer.race.Race;
import net.slintes.webracer.web.impl.server.WebServer;

/**
 * This class is responsible for connection web to race and starting everything up
 */
public class WebImpl {

    private final Race race;

    public WebImpl(Race race) {
        this.race = race;
    }

    public void start() {
        WebServer webServer = new WebServer(race);
        race.setUICallback(webServer);
        webServer.startServer();
    }

}
