package net.slintes.webracer.race;

import net.slintes.webracer.race.impl.RaceImpl;
import net.slintes.webracer.track.Track;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 *
 */
public class RaceActivator implements BundleActivator {

    private ServiceReference<?> trackReference;
    private ServiceRegistration<?> registration;

    public void start(BundleContext context) throws Exception {

        // we need a track
        trackReference = context.getServiceReference(Track.class.getName());
        Track track = (Track)context.getService(trackReference);

        Race race = new RaceImpl(track);

        // register race
        registration = context.registerService(Race.class.getName(), race, null);
    }

    public void stop(BundleContext context) throws Exception {
        context.ungetService(trackReference);
        registration.unregister();
    }

}
