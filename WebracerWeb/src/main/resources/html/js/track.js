;Quintus.WebracerTrack = function (Q) {

    // extend default TileLayer for custom collision handling
    Q.TileLayer.extend("RaceTileLayer", {
        collidableTile: function (tileNum) {
            // starting grid is uninteresting
            return tileNum > 0 && tileNum != TILE_STARTPOS;
        }
    });

    // the track
    Q.scene("track", function (stage) {
        stage.collisionLayer(
            new Q.RaceTileLayer({
                dataAsset: PATH + "data/track.json",
                sheet: "tiles",
                tileW: 9,
                tileH: 9
            }));

        // new function for round based gameplay, forward to player
        stage.nextRound = function () {
            if(this.player){
                this.player.RaceControl.nextRound();
            }
        }

        // create place to store other cars to
        stage.otherCars = {};

        // helper method for converting pixel / tile position
        var tilePos = function (col, row) {
            return { x: (col * TILESIZE)-5, y: (row * TILESIZE)+5 };
        }

        var addCar = function(data){
            var clientId = data[WSS_ADDCAR_CLIENTID];
            var xPos = data[WSS_ADDCAR_XPOS];
            var yPos = data[WSS_ADDCAR_YPOS];
            var startPos = data[WSS_ADDCAR_STARTPOS];

            if(Q.state.get(CLIENTID) == clientId){
                // our car
                var player = new Q.Player(tilePos(xPos, yPos));
                player.p.frame = startPos - 1;
                stage.player = stage.insert(player);

                // follow player
                stage.add("viewport").follow(player);
            }
            else {
                var otherCar = new Q.OtherCar(tilePos(xPos, yPos));
                otherCar.p.frame = startPos - 1;
                stage.otherCars[clientId] = stage.insert(otherCar);
            }

        }

        Q.state.on("change." + WSS_ADDCAR, addCar);

    });

};