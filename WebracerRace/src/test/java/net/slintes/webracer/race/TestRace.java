package net.slintes.webracer.race;

import net.slintes.webracer.db.WebracerDB;
import net.slintes.webracer.race.impl.RaceImpl;
import net.slintes.webracer.track.Track;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Test class for Race
 */
public class TestRace {

    private Track track;
    private WebracerDB db;
    private Race race;
    private UICallback callback;

    @Before
    public void setup() {

        // mock dependencies
        track = mock(Track.class);
        db = mock(WebracerDB.class);
        when(track.getTrack()).thenReturn("");

        // create race
        race = new RaceImpl(track, db);

        // mock ui callback
        callback = mock(UICallback.class);
        race.setUICallback(callback);
    }

    @Test
    public void basicTests() {

        // register client 1
        String clientId1 = "111";
        boolean registered = race.registerClient(clientId1);
        assertTrue(registered);

        // add car 1
        String name1 = "aaa";
        int startPos = race.registerCar(clientId1, name1);
        assertEquals(1, startPos);

        verify(callback).addCar(any(Car.class));

        // register client 2
        String clientId2 = "222";
        registered = race.registerClient(clientId2);
        assertTrue(registered);

        // verify getting car 1
        verify(callback).addCar(anyString(), any(Car.class));

        // add car 2
        String name2 = "bbb";
        startPos = race.registerCar(clientId2, name2);
        assertEquals(2, startPos);

        verify(callback, times(2)).addCar(any(Car.class));

        // wait for start
        verify(callback, timeout(11000)).start();

        // remove a car
        race.unRegisterClient(clientId1);
        verify(callback).removeCar(clientId1);

        // finish race
        race.finish(clientId2, 0, 0, 0);

        verify(callback, timeout(31000)).reset();

    }


}
