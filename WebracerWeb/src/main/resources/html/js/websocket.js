;Quintus.Websocket = function (Q) {

    // prepare websocket connection

    var connection;

    var connect = function() {

        var url = "ws://" + window.location.host + "/ws/";

        connection = new WebSocket(url);

        connection.onopen = function(){
            console.log("webSocket open");
            Q.state.set(WEBSOCKET_INIT_READY, true);
        }

        connection.onerror = function (error) {
            console.log("webSocket error: " + error);
            // TODO handle error
        };

        connection.onmessage = function (message) {
            console.log("received message: " + message.data);

            var jsonMessage = JSON.parse(message.data);
            var command = 'ws-' + jsonMessage.command;

            var data = {};
            if(jsonMessage.data){
                data = jsonMessage.data;
            }

            // set command to Quintus state, so we can add state change listeners
            // where we need to react on commands
            Q.state.set(command, data);
        };
    }

    // add send message to Q so that it's easy available everywhere
    var sendMessage = function(message) {
        console.log("sending message: " + message);
        connection.send(message);
    }

    // send message with command and data
    Q.sendCommand = function (command, data) {
        var jsonCommand = {};
        jsonCommand.command = command;

        if(data){
            jsonCommand.data = data;
        } else {
            jsonCommand.data = {};
        }

        var commandString = JSON.stringify(jsonCommand);
        sendMessage(commandString);
    }

    // generate some id
    var generateUUID = function () {
        var d = new Date().getTime();
        var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            var r = (d + Math.random() * 16) % 16 | 0;
            d = Math.floor(d / 16);
            return (c == 'x' ? r : (r & 0x7 | 0x8)).toString(16);
        });
        return uuid;
    };

    // register client when websocket connection is up and running AND Quintus ist ready
    var registerClient = function () {

        var clientId = generateUUID();
        Q.state.set(CLIENTID, clientId);

        var data = {};
        data[WSC_REGISTER_CLIENT_ID] = clientId;
        Q.sendCommand(WSC_REGISTER_CLIENT, data);

    }

    var initReady = function(){
        var websocketReady = Q.state.get(WEBSOCKET_INIT_READY);
        var quintusReady = Q.state.get(QUINTUS_INIT_READY);
        if(websocketReady && quintusReady){
            registerClient();
        }
    }

    Q.state.on("change." + WEBSOCKET_INIT_READY, initReady);
    Q.state.on("change." + QUINTUS_INIT_READY, initReady);

    // called from html form
    joinRace = function (form) {
        var name = form.name.value;
        if (name.trim().length == 0) {
            alert("Please enter your name");
            return false;
        }
        else {
            var data = {};
            data[WSC_REGISTER_CAR_NAME] = name;
            Q.sendCommand(WSC_REGISTER_CAR, data);
            return false;
        }
    }

    connect();

};