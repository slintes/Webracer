package net.slintes.webracer.race.impl;

import net.slintes.webracer.db.WebracerDB;
import net.slintes.webracer.race.Car;
import net.slintes.webracer.race.UICallback;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Start / stop timers, control the race
 */
public class RaceControl {

    private static final int SECONDS_TO_START = 10;
    private static final int MINUTES_TO_CANCEL_RACE = 15;
    private static final int MINUTES_TO_CANCEL_RACE_AFTER_WINNER = 5;
    private static final int SECONDS_TO_SHOW_RESULTS = 30;
    private static final int MINIMUM_NR_CARS = 2;

    private final UICallback uiCallBack;
    private final List<Client> cars;
    private final RaceDB raceDB;

    private int nrCars = 0;
    private int secondsToStart = SECONDS_TO_START;

    private RaceState state = RaceState.WAITING;

    private long startTime = 0;
    private long wonTime = 0;
    private long readyTime = 0;

    private String lastMessage = null;
    private int skipCount = 0;

    public RaceControl(UICallback uiCallBack, WebracerDB webracerDB, List<Client> cars) {
        this.uiCallBack = uiCallBack;
        this.cars = cars;

        this.raceDB = new RaceDB(webracerDB);

        // start a timer which checks every seconds if something is to do
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(this::checkState, 0, 1, TimeUnit.SECONDS);
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

    /**
     * call this when the first car finished the race
     */
    public void raceWon(Car car) {
        wonTime = startTime + car.getResultTime();
        state = RaceState.WON;
        uiCallBack.showMessage(car.getName() + " won the race! CONGRATULATIONS!");
    }

    /**
     * call this when others than the first car finished the race
     */
    public void raceFinished(Car car) {
        uiCallBack.showMessage(car.getName() + " finished the race on position " + car.getResultPosition() + " !");
    }


    public void crash(Car car){
        uiCallBack.showMessage(car.getName() + " crashed his car into the wall!");
    }

    /**
     * call this when all cars finished or crahed
     */
    public void raceReady(){
        readyTime = System.currentTimeMillis();
        state = RaceState.READY;

        // send results to clients
        String result = getResultString();
        String[] results = result.split("\n");
        Arrays.stream(results).forEach(uiCallBack::showMessage);

        // store results
        storeResults();
    }

    public long getRaceTime(){
        return System.currentTimeMillis() - startTime;
    }

    public RaceState getState(){
        return state;
    }

    private void handleWaitingState(){
        if(nrCars < MINIMUM_NR_CARS){
            // still waiting for cars
            showMessage("Waiting for players...");
            secondsToStart = SECONDS_TO_START;
        } else {
            // countdown to race
            if (secondsToStart == 0) {
                showMessage("GO!");
                startRace();
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

    private void startRace(){
        state = RaceState.RUNNING;
        startTime = System.currentTimeMillis();
        uiCallBack.start();
    }

    private void resetRace() {
        // reset everything so that a new race can begin
        secondsToStart = SECONDS_TO_START;
        nrCars = 0;
        state = RaceState.WAITING;
        cars.stream().forEach(
                c -> {
                    c.setStartPosition(0);
                    c.setCrashed(false);
                    c.setFinished(false);
                    c.setResultTime(0);
                    c.setResultPosition(9999);
                    c.setName(null);
                });
        uiCallBack.reset();
    }

    /**
     * always use this method for sending messagers
     * in order to avoid the same message (Waiting for players...) repeated too fast
     */
    private void showMessage(String message){
        if(message.equals(lastMessage) && skipCount++ < 5){
            return;
        }
        skipCount = 0;
        lastMessage = message;
        uiCallBack.showMessage(message);
    }

    private String getResultString() {
        // build astring containg the results of all cars
        StringBuilder result = new StringBuilder();
        result.append("Results: (next race in " + SECONDS_TO_SHOW_RESULTS + " seconds)\n");
        cars.stream()
                .filter(c -> c.getName() != null)
                .sorted((c1, c2) -> new Long(c1.getResultPosition()).compareTo(new Long(c2.getResultPosition())))
                .forEach(c -> result.append(getCarString(c)));

        return result.toString();
    }

    private String getCarString(Car c) {
        // build a string containing the result of a car
        String carString = "";
        boolean finished = c.getResultTime() > 0;
        carString += "  " + (finished ? c.getResultPosition() : "Out of race")
                + ": " + c.getName()
                + (finished ? " (" + getTimeString(c.getResultTime()) + ")\n" : "\n");
        return carString;
    }

    private String getTimeString(long resultInMs) {
        // convert duration in milliseconds to a string
        long h = resultInMs / 100 % 10;
        long s = (resultInMs / 1000) % 60;
        long m = (resultInMs / (60 * 1000)) % 60;
        return String.format("%d:%02d,%d", m, s, h);
    }

    private void storeResults(){
        raceDB.saveResults(startTime, cars);
    }

}
