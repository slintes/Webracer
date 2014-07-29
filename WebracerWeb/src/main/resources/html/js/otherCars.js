;Quintus.WebracerOtherCars = function (Q) {

    // behaviour of players car
    Q.component("RaceControlOther", {

        added: function () {
            var p = this.entity.p;

            // time per round in seconds
            p.stepDelay = TIMEOUT / 1000;

            // helper for keeping track of time
            p.stepWait = 0;

            // reigister event handlers
            this.entity.on("step", this, "step");
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

        nextRound: function (xPos, yPos) {

            // calculate next round

            var p = this.entity.p;

            // calculate x and y of movement
            p.diffX = xPos - p.x;
            p.diffY = yPos - p.y

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
    Q.Sprite.extend("OtherCar", {
        init: function (p) {
            this._super(p, {
                sheet: "car",
                frame: 0, // overwrite with startposition - 1 when added
                scale: 1.2, // make car a bit bigger
                angle: 90, // starting position: heading right
                broken: false, // track if car is broken = hit the wall
                finished: false, // track if car has finished
                gravityX: 0, // oh, no gravity please :)
                gravityY: 0
            });

            // add the 2d and our RaceControl component
            this.add("2d, RaceControlOther");
        }

    });


};