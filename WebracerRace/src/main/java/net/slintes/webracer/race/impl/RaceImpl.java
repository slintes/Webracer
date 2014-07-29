package net.slintes.webracer.race.impl;

import net.slintes.webracer.race.Race;
import net.slintes.webracer.race.UICallback;
import net.slintes.webracer.track.Track;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class RaceImpl implements Race {

    private static final int MAX_NR_CARS = 10;

    private final Track track;
    private UICallback uiCallback;
    private RaceControl raceControl;

    private final List<Client> clients = new ArrayList<>();

    public RaceImpl(Track track){
        System.out.println("new Race");
        this.track = track;
    }

    @Override
    public void setUICallback(UICallback uiCallback) {
        this.uiCallback = uiCallback;
        raceControl = new RaceControl(uiCallback);
    }

    @Override
    public String getTrack() {
        return track.getTrack();
    }

    @Override
    synchronized public boolean registerClient(String clientId) {
        clients.add(new Client(clientId));
        return raceControl.getState().equals(RaceState.WAITING) && !maxNrCarsReached();
    }

    @Override
    synchronized public void unRegisterClient(String clientId) {
        Client client = getClient(clientId);
        clients.remove(client);
        if(client.getName() != null){
            raceControl.removeCar(client.getName());
        }
    }

    @Override
    synchronized public int registerCar(String clientId, String name) {
        if(!raceControl.getState().equals(RaceState.WAITING) || maxNrCarsReached()){
            return 0;
        }

        Client client = getClient(clientId);
        int startPosition = getNrOfCars() + 1;
        client.setStartPosition(startPosition);
        client.setName(name);

        uiCallback.addCar(client);
        raceControl.addCar(client);

        return startPosition;
    }

    @Override
    synchronized public void nextPosition(String clientId, int xPos, int yPos) {
        Client client = updatePosition(clientId, xPos, yPos);
        uiCallback.updateCar(client);
    }

    @Override
    synchronized public void crash(String clientId, int xPos, int yPos) {
        Client client = updatePosition(clientId, xPos, yPos);
        client.setCrashed(true);
        uiCallback.updateCar(client);
        checkForDrivingCars();
    }


    @Override
    synchronized public void finish(String clientId, int xPos, int yPos) {
        Client client = updatePosition(clientId, xPos, yPos);
        client.setFinished(true);
        uiCallback.updateCar(client);

        raceControl.raceWon();

        checkForDrivingCars();
    }


    private Client getClient(String clientId){
        return clients.stream().filter(c -> c.getClientId().equals(clientId)).findFirst().get();
    }

    private boolean maxNrCarsReached(){
        return getNrOfCars() >= MAX_NR_CARS;
    }

    private int getNrOfCars(){
        return (int)clients.stream().filter(c -> c.getName() != null).count();
    }

    private Client updatePosition(String clientId, int xPos, int yPos){
        Client client = getClient(clientId);
        client.setxPos(xPos);
        client.setyPos(yPos);
        return client;
    }

    private void checkForDrivingCars() {
        // finish the race when all cars crashed or finished
        boolean drivingCars = clients.stream().anyMatch(c -> !c.isCrashed() && !c.isFinished());
        if(!drivingCars){
            raceControl.raceReady();
        }
    }
}
