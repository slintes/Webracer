package net.slintes.webracer.race;

/**
 * callback for communication from race to the ui
 */
public interface UICallback {

    public void sendMessage(String carId, String message);

}


