package net.slintes.webracer.web;

import net.slintes.webracer.race.Race;
import net.slintes.webracer.web.impl.WebImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 *
 */
public class WebActivator implements BundleActivator {

    private ServiceReference<?> raceReference;

    @Override
    public void start(BundleContext context) throws Exception {

        // get the race
        raceReference = context.getServiceReference(Race.class.getName());
        Race race = (Race)context.getService(raceReference);

        // start all the things :)
        WebImpl web = new WebImpl(race);
        web.start();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        context.ungetService(raceReference);
    }

}
