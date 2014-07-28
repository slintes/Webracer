window.addEventListener('load', function () {

    // init Quintus
    var Q = window.Q = Quintus()
        .include("Sprites, Scenes, Input, 2D, UI")
        .include("WebracerPlayer, WebracerTrack, WebracerTextStatus, WebracerGraphicalStatus")
        .setup({width: 800, height: 700})
        .controls(true);

    // helper method for converting pixel / tile position
    var TILESIZE = 9;
    Q.tilePos = function (col, row) {
        return { x: col * TILESIZE, y: row * TILESIZE };
    }

    // disable default collision handling of 2d component,
    // we handle it in our RaceControl component
    Q.components["2d"].prototype.collision = function (x, y) {};

    // preload all needed data, init some things when done
    Q.load(PATH + "images/track.png, " +
            PATH + "data/track.json, " +
            PATH + "images/cars.png, " +
            PATH + "data/cars.json, " +
            PATH + "images/steeringWheelAndGears.png, " +
            PATH + "data/steeringWheelAndGears.json",

        function () {
            // no gravity please :)
            Q.gravityX = 0;
            Q.gravityY = 0;

            // initial state
            Q.state.set({steering: STEERING_STRAIGHT});
            Q.state.set({steeringAngle: 90})
            Q.state.set({speed: 0});
            Q.state.set({onGrass: false});

            // the graphics of the track
            Q.sheet("tiles", PATH + "images/track.png", { tilew: TILESIZE, tileh: TILESIZE});

            // the cars
            Q.compileSheets(PATH + "images/cars.png", PATH + "data/cars.json");

            // steering wheel and gears
            Q.compileSheets(PATH + "images/steeringWheelAndGears.png", PATH + "data/steeringWheelAndGears.json");

            // init stage with the track
            Q.stageScene("track", 0);

            // init stage with steering and gear status
            Q.stageScene("graphicalStatus", 1);

            // init stage with infos
//            Q.stageScene("textStatus", 2);

            // start timeout
            window.setTimeout(nextRound, TIMEOUT);
        }
    );

    // handle round based gameplay
    this.nextRound = function () {
        Q.stage(0).nextRound();
        window.setTimeout(nextRound, TIMEOUT);
    }

}, true);
