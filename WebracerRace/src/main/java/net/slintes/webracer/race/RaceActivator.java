package net.slintes.webracer.race;

import net.slintes.webracer.car.CarFactory;
import net.slintes.webracer.race.impl.RaceImpl;
import net.slintes.webracer.web.Web;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 *
 */
public class RaceActivator implements BundleActivator {

    private ServiceReference<?> carFactoryReference;
    private ServiceReference<?> webReference;

    public void start(BundleContext context) throws Exception {
        carFactoryReference = context.getServiceReference(CarFactory.class.getName());
        CarFactory carFactory = (CarFactory)context.getService(carFactoryReference);

        webReference = context.getServiceReference(Web.class.getName());
        Web web = (Web)context.getService(webReference);

        new RaceImpl(carFactory, web);
    }

    public void stop(BundleContext context) throws Exception {
        context.ungetService(carFactoryReference);
    }

}
