package dk.grp1.tanks.weapon.nuke.internal;

import dk.grp1.tanks.common.services.IGamePluginService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class NukeActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {

        bundleContext.registerService(IGamePluginService.class.getName(), new NukeGamePlugin(),null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("STOPPING dk.grp1.tanks.weapon.nuke");
    }
}
