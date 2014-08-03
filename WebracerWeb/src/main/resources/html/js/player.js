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

            // register event handlers
            this.entity.on("hit", this, "collision");
            this.entity.on("step", this, "step");
        },

        // called by Quituns on collisions
        collision: function (col) {

            var p = this.entity.p;

            if(p.crashed || p.finished){
                return;
            }

            var tileNum = col.tile;

            // handle grass
            if (tileNum == TILE_GRASS) {
                if (Q.state.get(SPEED) == SPEED2) {
                    Q.state.set(SPEED, SPEED1);
                    Q.state.set(ONGRASS, true);
                }
            }
            else {
                Q.state.set(ONGRASS, false);
            }

            // handle wall
            if (tileNum == TILE_WALL) {
                p.crashed = true;

                // stop moving
                p.diffX = 0;
                p.diffY = 0;

                // send crash position to backend
                var data = {};
                data[WSC_UPDATE_POS_X] = Math.round(p.x);
                data[WSC_UPDATE_POS_Y] = Math.round(p.y);
                data[WSC_UPDATE_POS_SPEED] = 0;
                data[WSC_UPDATE_POS_ANGLE] = p.angle;
                data[WSC_UPDATE_POS_CRASHED] = true;
                data[WSC_UPDATE_POS_FINISHED] = false;
                Q.sendCommand(WSC_UPDATE_POS, data);

            }

            // handle sector 1
            if (tileNum == TILE_SECTOR1) {
                p.sector1passed = true;
            }
            // handle sector 2
            if (tileNum == TILE_SECTOR2) {
                p.sector2passed = true;
            }
            // handle finish
            if (tileNum == TILE_FINISH) {

                // check if both sectors have been passed
                if(p.sector1passed && p.sector2passed){
                    p.nrLapsDone++;
                    p.sector1passed = false;
                    p.sector2passed = false;

                    // race finished?
                    if(p.nrLapsDone == NR_LAPS){
                        p.finished = true;

                        // send finish position to backend
                        var data = {};
                        data[WSC_UPDATE_POS_X] = Math.round(p.x);
                        data[WSC_UPDATE_POS_Y] = Math.round(p.y);
                        data[WSC_UPDATE_POS_SPEED] = 0;
                        data[WSC_UPDATE_POS_ANGLE] = p.angle;
                        data[WSC_UPDATE_POS_CRASHED] = false;
                        data[WSC_UPDATE_POS_FINISHED] = true;
                        Q.sendCommand(WSC_UPDATE_POS, data);
                    }
                }
            }
        },

        // called permanently by Quitus engine
        step: function (dt) {

            var p = this.entity.p;

            if (p.crashed || p.finished) {
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
            var p = this.entity.p;

            if (p.crashed || p.finished) {
                return;
            }

            // get last pressed key and reset
            var lastKey = Q.state.get(LAST_KEY);
            Q.state.set(LAST_KEY, KEY_NONE);

            // set new steering
            var steering = Q.state.get(STEERING);
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
            Q.state.set(STEERING, steering);

            // rotate car according to steering
            var angleChanged = false;
            if (steering == STEERING_LEFT) {
                p.angle -= DEGREES;
                angleChanged = true;
            } else if (steering == STEERING_RIGHT) {
                p.angle += DEGREES;
                angleChanged = true;
            }
            Q.state.set(STEERING_ANGLE, p.angle);

            // set new speed
            var speed = Q.state.get(SPEED);
            if (lastKey == KEY_UP) {
                if (speed == 0) {
                    speed = SPEED1;
                } else if (speed == SPEED1) {
                    // prevent speeds2 on grass
                    if (!Q.state.get(ONGRASS)) {
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
            Q.state.set(SPEED, speed);
            p.stepDistance = speed;

            // calculate x and y of movement
            p.diffX = Math.round( (Math.sin(2 * Math.PI * p.angle / 360) * p.stepDistance) );
            p.diffY = Math.round( -(Math.cos(2 * Math.PI * p.angle / 360) * p.stepDistance) );

            // prepare movement
            if (p.diffY || p.diffX) {
                p.stepping = true;
                p.origX = p.x;
                p.origY = p.y;
                p.destX = p.x + p.diffX;
                p.destY = p.y + p.diffY;
                p.stepWait = p.stepDelay;

                // send new position to backend
                var data = {};
                data[WSC_UPDATE_POS_X] = Math.round(p.destX);
                data[WSC_UPDATE_POS_Y] = Math.round(p.destY);
                data[WSC_UPDATE_POS_SPEED] = p.stepDistance;
                data[WSC_UPDATE_POS_ANGLE] = p.angle;
                data[WSC_UPDATE_POS_CRASHED] = p.crashed;
                data[WSC_UPDATE_POS_FINISHED] = p.finished;
                Q.sendCommand(WSC_UPDATE_POS, data);

            } else if(angleChanged){

                // we are not moving, but we rotated
                var data = {};
                data[WSC_UPDATE_POS_X] = Math.round(p.x);
                data[WSC_UPDATE_POS_Y] = Math.round(p.y);
                data[WSC_UPDATE_POS_SPEED] = p.stepDistance;
                data[WSC_UPDATE_POS_ANGLE] = p.angle;
                data[WSC_UPDATE_POS_CRASHED] = p.crashed;
                data[WSC_UPDATE_POS_FINISHED] = p.finished;
                Q.sendCommand(WSC_UPDATE_POS, data);

            }

        }

    });

    // the player's sprite
    Q.Sprite.extend("Player", {
        init: function (p) {
            this._super(p, {
                sheet: "car",
                frame: 0, // override with startpos - 1 when added
                scale: 1.2, // make car a bit bigger
                angle: 90, // starting position: heading right
                crashed: false, // track if car has crashed
                finished: false, // track if car has finished
                sector1passed: false, // track if car has passed sector 1
                sector2passed: false, // track if car has passed sector 2
                nrLapsDone: 0, // track how many laps where driven
                gravityX: 0, // oh, no gravity please :)
                gravityY: 0
            });

            // add the 2d and our RaceControl component
            this.add("2d, RaceControl");

            // save the latest key press
            var upPressed = function () {
                Q.state.set(LAST_KEY, KEY_UP);
            };
            var downPressed = function () {
                Q.state.set(LAST_KEY, KEY_DOWN);
            };
            var leftPressed = function () {
                Q.state.set(LAST_KEY, KEY_LEFT);
            };
            var rightPressed = function () {
                Q.state.set(LAST_KEY, KEY_RIGHT);
            };

            Q.input.on("up", this, upPressed);
            Q.input.on("down", this, downPressed);
            Q.input.on("left", this, leftPressed);
            Q.input.on("right", this, rightPressed);

        }

    });

};