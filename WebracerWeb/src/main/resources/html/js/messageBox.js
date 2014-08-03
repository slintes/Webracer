;Quintus.WebracerMessageBox = function (Q) {

    Q.scene('messageBox', function (stage) {

        // always show the last x message
        // init empty messages
        var nrMessages = 12;
        var firstLine = "Messages from Race Control:                                                      ";
        var messages = [];
        messages.push(firstLine);
        for(var i=0; i<nrMessages; i++){
            messages.push("");
        }

        // create a box
        var box = stage.insert(new Q.UI.Container({
            fill: "rgba(255,0,0,0.4)",
            border: 5,
            x: 500,
            y: 380,
            scale: 0.6
        }));

        // add text to box
        var infotext = box.insert(
            new Q.UI.Text({x: 0, y: 0, align: "left", label: messages.join('\n')})
        );

        box.fit(20);

        // method for updating the text
        var updateMessageBox = function (data) {
            var message = data[WSS_MESSAGE_MESSAGE];

            // get current time
            var date = new Date();
            var hours = date.getHours();
            hours = hours < 10 ? "0" + hours : hours;
            var minutes = date.getMinutes();
            minutes = minutes < 10 ? "0" + minutes : minutes;
            var seconds = date.getSeconds();
            seconds = seconds < 10 ? "0" + seconds : seconds;
            var time = hours + ":" + minutes + ":" + seconds;

            // remove first 2 lines, re-add header line
            messages.shift();
            messages.shift();
            messages.unshift(firstLine);

            // add new line
            messages.push(time + " : " + message);

            // set text
            infotext.p.label = messages.join("\n");
        };

        // register listener
        Q.state.on("change." + WSS_MESSAGE, updateMessageBox);
    });

};