package net.slintes.webracer.race.impl;

import net.slintes.webracer.race.Car;
import net.slintes.webracer.race.UICallback;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * start / stop timers, control the race
 */
public class RaceControl {

    private static final int SECONDS_TO_START = 10;
    private static final int MINUTES_TO_CANCEL_RACE = 15;
    private static final int MINUTES_TO_CANCEL_RACE_AFTER_WINNER = 5;
    private static final int SECONDS_TO_SHOW_RESULTS = 30;
    private static final int MINIMUM_NR_CARS = 2;

    private final UICallback uiCallBack;

    int nrCars = 0;
    int secondsToStart = SECONDS_TO_START;

    private RaceState state = RaceState.WAITING;

    long startTime = 0;
    long wonTime = 0;
    long readyTime = 0;

    private String lastMessage = null;
    private int skipCount = 0;


    public RaceControl(UICallback uiCallBack) {
        this.uiCallBack = uiCallBack;

        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(() -> {
            checkState();
        }, 0, 1, TimeUnit.SECONDS);
    }

    synchronized private void checkState() {
        switch(state){
            case WAITING:
                handleWaitingState();
                break;
            case RUNNING:
                handleRunningState();
                break;
            case WON:
                handleWonState();
                break;
            case READY:
                handleReadyState();
                break;
        }
    }

    public void addCar(Car car){
        nrCars++;
        secondsToStart = SECONDS_TO_START;
        showMessage(car.getName() + " joined the race on start position " + car.getStartPosition());
    }

    public void removeCar(String name) {
        nrCars--;
        showMessage(name + " left the race");
    }

    public void raceFinished(Client car) {
        uiCallBack.showMessage(car.getName() + " finished the race on position " + car.getResultPosition() + " !");
    }

    public void raceWon(Car car) {
        wonTime = startTime + car.getResultTime();
        state = RaceState.WON;
        uiCallBack.showMessage(car.getName() + " won the race! CONGRATULATIONS!");
    }

    public void crash(Car car){
        uiCallBack.showMessage(car.getName() + " crashed his car into the wall!");
    }

    public void raceReady(){
        readyTime = System.currentTimeMillis();
        state = RaceState.READY;
        uiCallBack.showResults();
    }

    public long getRaceTime(){
        return System.currentTimeMillis() - startTime;
    }

    public RaceState getState(){
        return state;
    }

    private void handleWaitingState(){
        if(nrCars < MINIMUM_NR_CARS){
            showMessage("Waiting for players...");
            secondsToStart = SECONDS_TO_START;
        } else {
            if (secondsToStart == 0) {
                showMessage("GO!");
                start();
            } else {
                showMessage("Race will start in " + secondsToStart + " seconds");
                secondsToStart--;
            }
        }
    }

    private void handleRunningState(){
        // check if race timed out
        long time = System.currentTimeMillis() - startTime;
        if (time >= MINUTES_TO_CANCEL_RACE * 60 * 1000) {
            // show message and reset all clients
            showMessage("Race timed out");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
            }
            resetRace();
        }
    }

    private void handleWonState() {
        // check if race timed out
        long time = System.currentTimeMillis() - wonTime;
        if (time >= MINUTES_TO_CANCEL_RACE_AFTER_WINNER * 60 * 1000) {
            // show message and reset all clients
            showMessage("Race timed out");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
            }
            raceReady();
        }
    }

    private void handleReadyState() {
        // check if a new race has to start
        long time = System.currentTimeMillis() - readyTime;
        if (time >= SECONDS_TO_SHOW_RESULTS * 1000) {
            resetRace();
        }

    }

    private void start(){
        state = RaceState.RUNNING;
        startTime = System.currentTimeMillis();
        uiCallBack.start();
    }

    private void resetRace() {
        secondsToStart = SECONDS_TO_START;
        nrCars = 0;
        state = RaceState.WAITING;
        uiCallBack.reset();
    }

    // always use this in order to avoid the same message (Waiting for players...) repeated too fast
    private void showMessage(String message){
        if(message.equals(lastMessage) && skipCount++ < 5){
            return;
        }
        skipCount = 0;
        lastMessage = message;
        uiCallBack.showMessage(message);
    }

}
