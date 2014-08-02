package net.slintes.webracer.db.file;

import net.slintes.webracer.db.WebracerDB;
import net.slintes.webracer.db.file.impl.WebracerFileDB;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * Activator for file based DB
 */
public class FileDBActivator implements BundleActivator {

    private ServiceRegistration<?> registration;

    @Override
    public void start(BundleContext context) throws Exception {
        WebracerDB fileDB = new WebracerFileDB();
        registration = context.registerService(WebracerDB.class.getName(), fileDB, null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        registration.unregister();
    }
}
