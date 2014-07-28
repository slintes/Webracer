// some global constants, DO NOT EDIT

// path to resources
var PATH = window.location.pathname;

// tile type numbers
var TILE_ASPHALT = 1;
var TILE_GRASS = 2;
var TILE_WALL = 3;
var TILE_FINISH = 4;
var TILE_STARTPOS = 5;
var TILE_SECTOR1 = 6;
var TILE_SECTOR2 = 7;

// steering state
var STEERING = "steering"; // state name
var STEERING_STRAIGHT = "straight"; // state value
var STEERING_LEFT = "left"; // state value
var STEERING_RIGHT = "right"; // state value

// pressed key state
var LAST_KEY = "lastKey"; // state name
var KEY_UP = "acc"; // state value
var KEY_DOWN = "brake"; // state value
var KEY_LEFT = "left"; // state value
var KEY_RIGHT = "right"; // state value
var KEY_NONE = "none"; // state value

// some more state names with int / boolean values
var STEERING_ANGLE = "steeringAngle";
var SPEED = "speed";
var ONGRASS = "onGrass";

// websocket server commands
var WS_MESSAGE = "ws-message";


