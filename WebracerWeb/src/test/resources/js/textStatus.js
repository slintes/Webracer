;Quintus.WebracerTextStatus = function (Q) {

    Q.scene('testStatus',function(stage) {
        var box = stage.insert(new Q.UI.Container({
            x: 0, y: 0, fill: "rgba(0, 0, 0, 0.3)"
        }));
        var infotext = box.insert(
                new Q.UI.Text({x: 200, y: 70, label: "                                            \n \n "})
        );
        box.fit(5);

        var updateStatus = function() {
            infotext.p.label = "speed: " + Q.state.get("speed") +
                "\nsteering: " + Q.state.get("steering") +
                "\nnextCommand: " + Q.state.get("lastKey");
        };

        Q.state.on("change.speed", updateStatus);
        Q.state.on("change.steering", updateStatus);
        Q.state.on("change.lastKey", updateStatus);

    });

};