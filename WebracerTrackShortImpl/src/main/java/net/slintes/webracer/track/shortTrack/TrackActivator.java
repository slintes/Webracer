package net.slintes.webracer.track.shortTrack;

import net.slintes.webracer.track.Track;
import net.slintes.webracer.track.shortTrack.impl.TrackShort;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 *
 */
public class TrackActivator implements BundleActivator {

    private ServiceRegistration<?> registration;

    @Override
    public void start(BundleContext context) throws Exception {
        Track track = new TrackShort();
        registration = context.registerService(Track.class.getName(), track, null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        registration.unregister();
    }
}
