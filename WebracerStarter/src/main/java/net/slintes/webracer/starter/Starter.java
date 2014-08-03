package net.slintes.webracer.starter;

import org.apache.felix.framework.Felix;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Starts a OSGi container and installs and starts all needed bundles
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
            Bundle track = context.installBundle("file:repoOsgi/WebracerTrack.jar");
            Bundle trackLongImpl = context.installBundle("file:repoOsgi/WebracerTrackLongImpl.jar");
            Bundle trackShortImpl = context.installBundle("file:repoOsgi/WebracerTrackShortImpl.jar");
            Bundle db = context.installBundle("file:repoOsgi/WebracerDB.jar");
            Bundle fileDbImpl = context.installBundle("file:repoOsgi/WebracerDBFileImpl.jar");
            Bundle race = context.installBundle("file:repoOsgi/WebracerRace.jar");
            Bundle web = context.installBundle("file:repoOsgi/WebracerWeb.jar");

            // Start and stop framework and bundles
            framework.start();

            // order is important...
            track.start();
//            trackLongImpl.start();
            trackShortImpl.start();
            db.start();
            fileDbImpl.start();
            race.start();
            web.start();

        }
        catch (Exception exception)
        {
            System.err.println("Error while executing program: " + exception);
            exception.printStackTrace();
            System.exit(0);
        }
    }

}
