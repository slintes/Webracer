;Quintus.WebracerGraphicalStatus = function (Q) {

    // the steering wheel
    Q.Sprite.extend("SteeringWheel", {
        init: function (p) {
            this._super(p, {
                sheet: "wheel",
                scale: scaleWheelAndGears,
                angle: 0
            });
        }
    });

    // gear N
    Q.Sprite.extend("GearN", {
        init: function (p) {
            this._super(p, {
                sheet: "gearN",
                scale: scaleWheelAndGears,
                angle: 0
            });
        }
    });

    // gear 1
    Q.Sprite.extend("Gear1", {
        init: function (p) {
            this._super(p, {
                sheet: "gear1",
                scale: scaleWheelAndGears,
                angle: 0
            });
        }
    });

    // gear 2
    Q.Sprite.extend("Gear2", {
        init: function (p) {
            this._super(p, {
                sheet: "gear2",
                scale: scaleWheelAndGears,
                angle: 0
            });
        }
    });

    // no command ("empty" arrow)
    Q.Sprite.extend("NoCommand", {
        init: function (p) {
            this._super(p, {
                sheet: "arrowEmpty",
                scale: scaleWheelAndGears,
                angle: 0
            });
        }
    });

    // command (arrow)
    Q.Sprite.extend("Command", {
        init: function (p) {
            this._super(p, {
                sheet: "arrow",
                scale: scaleWheelAndGears,
                angle: 0
            });
        }
    });

    Q.scene('graphicalStatus', function (stage) {
        box = stage.insert(new Q.UI.Container({
            x: 400, y: 100, width: 400, height: 200, fill: "rgba(0, 0, 0, 0.1)"
        }));


        updateStatus = function () {
        }

        Q.state.on("change.speed", updateStatus);
        Q.state.on("change.steering", updateStatus);
        Q.state.on("change.lastKey", updateStatus);

    });

};