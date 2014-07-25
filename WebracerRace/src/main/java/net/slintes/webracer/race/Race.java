package net.slintes.webracer.race;

/**
 *
 */
public interface Race {

    public void setUICallback(UICallback uiCallback);
    public String getTrack();
    public void registerCar(String id);
    public void unRegisterCar(String id);

}
