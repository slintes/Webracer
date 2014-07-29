// some global constants, DO NOT EDIT

// path to resources
var PATH = window.location.pathname;

// pixel size of a track tile
var TILESIZE = 9;

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
var CLIENTID = "clientId";

// websocket server commands
var WSS_MESSAGE = "ws-message"; // command name
var WSS_MESSAGE_MESSAGE = "message"; // value name

var WSS_ADDCAR = "ws-addCar"; // command name
var WSS_ADDCAR_CLIENTID = "clientId"; // value name
var WSS_ADDCAR_NAME = "name"; // value name
var WSS_ADDCAR_STARTPOS = "startPosition"; // value name
var WSS_ADDCAR_XPOS = "startPositionX"; // value name
var WSS_ADDCAR_YPOS = "startPositionY"; // value name

// websocket client commands
var WSC_REGISTER_CLIENT = "registerClient"; // command name
var WSC_REGISTER_CLIENT_ID = "clientId"; // value name

var WSC_REGISTER_CAR = "registerCar"; // command name
var WSC_REGISTER_CAR_NAME = "name"; // value name

