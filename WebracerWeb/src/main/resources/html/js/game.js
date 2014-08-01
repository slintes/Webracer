window.addEventListener('load', function () {

    // init Quintus
    var Q = window.Q = Quintus()
        .include("Sprites, Scenes, Input, 2D, UI") // Quintus
        .include("WebracerPlayer, WebracerOtherCars, WebracerTrack, Websocket") // own
        .include("WebracerTextStatus, WebracerGraphicalStatus, WebracerMessageBox") // more own
        //.setup({width: 900, height: 600})
        .setup({width: 850, height: 600})
        .controls(true);


    // disable default collision handling of 2d component,
    // we handle it in our RaceControl component
    Q.components["2d"].prototype.collision = function (x, y) {};

    // preload all needed data, init some things when done
    Q.load(PATH + "images/track.png, " +
            PATH + "track/track.json, " +
            PATH + "images/cars.png, " +
            PATH + "data/cars.json, " +
            PATH + "images/steeringWheelAndGears.png, " +
            PATH + "data/steeringWheelAndGears.json",

        function () {
            // no gravity please :)
            Q.gravityX = 0;
            Q.gravityY = 0;

            // initial state
            Q.state.set(STEERING, STEERING_STRAIGHT);
            Q.state.set(STEERING_ANGLE, 90)
            Q.state.set(SPEED, 0);
            Q.state.set(ONGRASS, false);

            // the graphics of the track
            Q.sheet("tiles", PATH + "images/track.png", { tilew: TILESIZE, tileh: TILESIZE});

            // the cars
            Q.compileSheets(PATH + "images/cars.png", PATH + "data/cars.json");

            // steering wheel and gears
            Q.compileSheets(PATH + "images/steeringWheelAndGears.png", PATH + "data/steeringWheelAndGears.json");

            var sceneNr = 0;

            // init stage with the track
            Q.stageScene("track", sceneNr++);

            // init stage with steering and gear status
            Q.stageScene("graphicalStatus", sceneNr++);

            // init stage with steering and gear status
            Q.stageScene("messageBox", sceneNr++);

            // init stage with infos
//            Q.stageScene("textStatus", sceneNr++);

            // start timeout
//            window.setTimeout(nextRound, TIMEOUT);

            // handle round based gameplay
            var nextRound = function () {
                Q.stage(0).nextRound();
                window.setTimeout(nextRound, TIMEOUT);
            }

            // add listener on start command for starting the race
            Q.state.on("change." + WSS_START, nextRound);

            Q.state.set(QUINTUS_INIT_READY, true);
        }
    );

}, true);
