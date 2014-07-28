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

        // add our car
        stage.player = stage.insert(new Q.Player(Q.tilePos(50, 35)));

        stage.add("viewport").follow(stage.player);

        // new function for round based gameplay, forward to player
        stage.nextRound = function () {
            this.player.RaceControl.nextRound();
        }

    });

};