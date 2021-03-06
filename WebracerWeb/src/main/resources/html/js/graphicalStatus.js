;Quintus.WebracerGraphicalStatus = function (Q) {

    // the steering wheel (will be rotated)
    Q.Sprite.extend("SteeringWheel", {
        init: function (p) {
            this._super(p, {
                sheet: "wheel"
            });
        }
    });

    // gear N, 1 and 2 (3 frames)
    Q.Sprite.extend("Gear", {
        init: function (p) {
            this._super(p, {
                sheet: "gear"
            });
        }
    });

    // command arrow (2 frames, 1st with arrow (will be rotated), 2nd no arrow)
    Q.Sprite.extend("Command", {
        init: function (p) {
            this._super(p, {
                sheet: "arrow"
            });
        }
    });

    Q.scene('graphicalStatus', function (stage) {

        // create a box
        var box = stage.insert(new Q.UI.Container({
            fill: "rgba(0,0,255,0.4)",
            border: 5,
            x: 100,
            y: 320,
            scale: 0.5
        }));

        // add everything to box
        var steering = box.insert(new Q.SteeringWheel({x: 0, y: 0}));
        var gear = box.insert(new Q.Gear({x: 150, y: 0}));
        var command = box.insert(new Q.Command({x: 40, y: 250, frame: 1}));

        box.fit(20);

        // define methods for updating the status
        var updateSteering = function () {
            var st = Q.state.get(STEERING);
            if(st == STEERING_STRAIGHT){
                steering.p.angle = 0;
            }
            if (st == STEERING_LEFT) {
                steering.p.angle = -DEGREES;
            }
            if (st == STEERING_RIGHT) {
                steering.p.angle = DEGREES;
            }
        };

        var updateSpeed = function () {
            var speed = Q.state.get(SPEED);
            if(speed == 0){
                gear.p.frame = 0;
            }
            else if (speed == SPEED1) {
                gear.p.frame = 1;
            }
            else if (speed == SPEED2) {
                gear.p.frame = 2;
            }
        };

        var updateCommand = function () {
            var lastKey = Q.state.get(LAST_KEY);
            if(lastKey == KEY_NONE){
                command.p.frame = 1;
            }
            else if (lastKey == KEY_UP) {
                command.p.frame = 0;
                command.p.angle = 0;
            }
            if (lastKey == KEY_RIGHT) {
                command.p.frame = 0;
                command.p.angle = 90;
            }
            if (lastKey == KEY_DOWN) {
                command.p.frame = 0;
                command.p.angle = 180;
            }
            if (lastKey == KEY_LEFT) {
                command.p.frame = 0;
                command.p.angle = 270;
            }
        };

        // register listeners
        Q.state.on("change." + STEERING, updateSteering);
        Q.state.on("change." + SPEED, updateSpeed);
        Q.state.on("change." + LAST_KEY, updateCommand);

    });

};