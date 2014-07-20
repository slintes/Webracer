package net.slintes.webracer.starter;

import org.apache.felix.framework.Felix;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Starter {

    public static void main(String[] args)
    {
        try
        {
            // Initialize Apache Felix Framework
            Map<String, String> configMap = new HashMap<>();
            configMap.put(Constants.FRAMEWORK_STORAGE_CLEAN, "onFirstInit");
            Felix framework = new Felix(configMap);
            framework.init();

            // Install bundles
            BundleContext context = framework.getBundleContext();
            Bundle race = context.installBundle("file:repoOsgi/WebracerRace.jar");
            Bundle web = context.installBundle("file:repoOsgi/WebracerWeb.jar");
            Bundle car = context.installBundle("file:repoOsgi/WebracerCar.jar");

            // Start and stop framework and bundles
            framework.start();
            car.start();
            web.start();
            race.start();

//            framework.stop();
        }
        catch (Exception exception)
        {
            System.err.println("Error while executing program: " + exception);
            exception.printStackTrace();
            System.exit(0);
        }
    }

}
