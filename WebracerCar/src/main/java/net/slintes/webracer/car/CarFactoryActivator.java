package net.slintes.webracer.car;

import net.slintes.webracer.car.impl.CarFactoryImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 *
 */
public class CarFactoryActivator implements BundleActivator {

    private ServiceRegistration<?> registration;

    @Override
    public void start(BundleContext context) throws Exception {
        CarFactory carFactory = new CarFactoryImpl();
        registration = context.registerService(CarFactory.class.getName(), carFactory, null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        registration.unregister();
    }

}
