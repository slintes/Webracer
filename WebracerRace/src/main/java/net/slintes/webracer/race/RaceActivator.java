package net.slintes.webracer.race;

import net.slintes.webracer.car.CarFactory;
import net.slintes.webracer.race.impl.RaceImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 *
 */
public class RaceActivator implements BundleActivator {

    private ServiceReference<?> serviceReference;

    public void start(BundleContext context) throws Exception {
        serviceReference = context.getServiceReference(CarFactory.class.getName());
        CarFactory carFactory = (CarFactory)context.getService(serviceReference);

        new RaceImpl(carFactory);
    }

    public void stop(BundleContext context) throws Exception {
        context.ungetService(serviceReference);
    }

}
