;Quintus.WebracerPlayer = function (Q) {

    // behaviour of players car
    Q.component("RaceControl", {

        added: function () {
            var p = this.entity.p;

            // speed
            p.stepDistance = 0;

            // time per round in seconds
            p.stepDelay = TIMEOUT / 1000;

            // helper for keeping track of time
            p.stepWait = 0;

            // reigister event handlers
            this.entity.on("hit", this, "collision");
            this.entity.on("step", this, "step");
        },

        collision: function (col) {

            var p = this.entity.p;
            var tileNum = col.tile;

            // handle grass
            if (tileNum == TILE_GRASS) {
                if (Q.state.get("speed") == SPEED2) {
                    Q.state.set({speed: SPEED1});
                    Q.state.set({onGrass: true});
                }
            }
            else {
                Q.state.set({onGrass: false})
            }

            // handle wall
            if (tileNum == TILE_WALL) {
                p.broken = true;
                p.diffX = 0;
                p.diffY = 0;
            }

            // handle sector 1
            if (tileNum == TILE_SECTOR1) {
                // TODO
            }
            // handle sector 2
            if (tileNum == TILE_SECTOR2) {
                // TODO
            }
            // handle finish
            if (tileNum == TILE_FINISH) {
                // TODO
            }
        },

        step: function (dt) {

            var p = this.entity.p;

            if (p.broken) {
                return;
            }

            // keep track of time
            p.stepWait -= dt;

            // move
            if (p.stepping) {
                p.x += p.diffX * dt / p.stepDelay;
                p.y += p.diffY * dt / p.stepDelay;
            }

            // next round?
            if (p.stepWait > 0) {
                return;
            }

            // move to end position if not there yet
            if (p.stepping) {
                p.x = p.destX;
                p.y = p.destY;
            }

            // stop moving
            p.stepping = false;
            p.diffX = 0;
            p.diffY = 0;

        },

        nextRound: function () {

            // calculate next round

//            console.log("player step");

            var p = this.entity.p;

            if (p.broken) {
                return;
            }

            // get last pressed key and reset
            var lastKey = Q.state.get("lastKey");
            Q.state.set({lastKey: KEY_NONE});

            // set new steering
            var steering = Q.state.get("steering");
            if (lastKey == KEY_LEFT) {
                if (steering == STEERING_RIGHT) {
                    steering = STEERING_STRAIGHT;
                } else if (steering == STEERING_STRAIGHT) {
                    steering = STEERING_LEFT;
                }
            } else if (lastKey == KEY_RIGHT) {
                if (steering == STEERING_LEFT) {
                    steering = STEERING_STRAIGHT;
                } else if (steering == STEERING_STRAIGHT) {
                    steering = STEERING_RIGHT;
                }
            }
            Q.state.set({steering: steering});

            // rotate car according to steering
            if (steering == STEERING_LEFT) {
                p.angle -= DEGREES;
            } else if (steering == STEERING_RIGHT) {
                p.angle += DEGREES;
            }
            Q.state.set({steeringAngle: p.angle});

            // set new speed
            var speed = Q.state.get("speed");
            if (lastKey == KEY_UP) {
                if (speed == 0) {
                    speed = SPEED1;
                } else if (speed == SPEED1) {
                    // prevent speeds2 on grass
                    if (!Q.state.get("onGrass")) {
                        speed = SPEED2;
                    }
                }
            } else if (lastKey == KEY_DOWN) {
                if (speed == SPEED2) {
                    speed = SPEED1;
                } else if (speed == SPEED1) {
                    speed = 0;
                }
            }
            Q.state.set({speed: speed});
            p.stepDistance = speed;

            // calculate x and y of movement
            p.diffX = (Math.sin(2 * Math.PI * p.angle / 360) * p.stepDistance);
            p.diffY = -(Math.cos(2 * Math.PI * p.angle / 360) * p.stepDistance);

            // prepare movement
            if (p.diffY || p.diffX) {
                p.stepping = true;
                p.origX = p.x;
                p.origY = p.y;
                p.destX = p.x + p.diffX;
                p.destY = p.y + p.diffY;
                p.stepWait = p.stepDelay;
            }

        }

    });

    // the player
    Q.Sprite.extend("Player", {
        init: function (p) {
            this._super(p, {
                sheet: "car1",
                scale: 1.2, // make car a bit bigger
                angle: 90, // starting position: heading right
                broken: false, // track if car is broken = hit the wall
                finished: false, // track if car has finished
                gravityX: 0, // oh, no gravity please :)
                gravityY: 0
            });

            // add the 2d and our RaceControl component
            this.add("2d, RaceControl");

            // save the latest key press
            var upPressed = function () {
                Q.state.set({lastKey: KEY_UP});
            };
            var downPressed = function () {
                Q.state.set({lastKey: KEY_DOWN});
            };
            var leftPressed = function () {
                Q.state.set({lastKey: KEY_LEFT});
            };
            var rightPressed = function () {
                Q.state.set({lastKey: KEY_RIGHT});
            };

            Q.input.on("up", this, upPressed);
            Q.input.on("down", this, downPressed);
            Q.input.on("left", this, leftPressed);
            Q.input.on("right", this, rightPressed);

        }

    });


};