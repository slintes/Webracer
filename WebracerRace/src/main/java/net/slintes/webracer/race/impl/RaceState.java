package net.slintes.webracer.race.impl;

/**
 * possible race states
 */
public enum RaceState {

    WAITING, // waiting for cars
    RUNNING, // race is running
    WON,     // we have a winner
    READY    // all cars finished or crashed

}
