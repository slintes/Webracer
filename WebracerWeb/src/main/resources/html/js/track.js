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

        // helper method for converting pixel / tile position
        var tilePos = function (col, row) {
            return { x: (col * TILESIZE)-5, y: (row * TILESIZE)+5 };
        }

        var addCar = function(data){
            var clientId = data[WSS_ADDCAR_CLIENTID];
            var xPos = data[WSS_ADDCAR_XPOS];
            var yPos = data[WSS_ADDCAR_YPOS];

            if(Q.state.get(CLIENTID) == clientId){
                // our car
                stage.player = stage.insert(new Q.Player(tilePos(xPos, yPos)));
                stage.add("viewport").follow(stage.player);
            }
            else {
                // TODO other car
            }

        }

        Q.state.on("change." + WSS_ADDCAR, addCar);

    });

};