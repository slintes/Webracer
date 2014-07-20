package net.slintes.webracer.web.impl;

/**
 *
 */
public class WebVerticle {

    private final WebImpl web;
//    List<SockJSSocket> clientSockets = new CopyOnWriteArrayList<>();

    public WebVerticle(WebImpl web) {
        this.web = web;
    }

    public void start() {

        web.registerClient("testid");

//        Vertx vertx = VertxFactory.newVertx();

//        DefaultVertxFactory defaultVertxFactory = new DefaultVertxFactory();
//        Vertx vertx = defaultVertxFactory.createVertx();
//
//        HttpServer server = vertx.createHttpServer();
//
//        server.requestHandler(req -> {
//            if (req.path().equals("/")){
//                req.response().sendFile("index.html"); // Serve the html
//            }
//        });
//
//        SockJSServer sockServer = vertx.createSockJSServer(server);
//
//        sockServer.installApp(
//                new JsonObject().putString("prefix", "/webracer"),
//                sock -> {
//                    clientSockets.add(sock);
//                    web.registerClient(sock.writeHandlerID());
//
//                    // echo
////                    sock.dataHandler(data -> sock.write(data));
//
//                    // socket ends
//                    sock.endHandler(event -> clientSockets.remove(sock));
//                });
//
//        server.listen(8080);
    }

    public void sendMessage(String clientId, String message) {

        System.out.println("message to client " + clientId + ": " + message);

//        clientSockets.stream().
//                filter(socket -> socket.writeHandlerID().equals(clientId)).
//                forEach(socket -> socket.write(new Buffer(message)));

    }

}
