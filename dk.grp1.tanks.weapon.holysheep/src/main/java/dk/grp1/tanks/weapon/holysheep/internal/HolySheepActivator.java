package dk.grp1.tanks.weapon.holysheep.internal;

import dk.grp1.tanks.common.services.IGamePluginService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HolySheepActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        bundleContext.registerService(IGamePluginService.class.getName(), new HolySheepPlugin(), null);
        System.out.println("STARTING dk.grp1.tanks.weapon.holysheep");
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("STOPPING dk.grp1.tanks.weapon.holysheep");
    }
}
