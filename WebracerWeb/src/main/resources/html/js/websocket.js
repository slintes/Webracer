;Quintus.Websocket = function (Q) {

    // prepare websocket connection

    var connection;

    connect = function() {

        var url = "ws://" + window.location.host + "/ws/";

        connection = new WebSocket(url);

        connection.onerror = function (error) {
            console.log("webSocket error: " + error);
            // TODO handle error
        };

        connection.onmessage = function (message) {
            console.log("received message: " + message.data);

            var command = message.data.command;
            var data = message.data.data;

            // set command to Quintus state, so we can add state change listeners
            // where we need to react on commands
            Q.state.set(command, data);
        };
    }

    // add send message to Q so that it's easy available everywhere
    Q.sendMessage = function(message) {
        console.log("sending message: " + message);
        connection.send(message);
    }

    connect();

};