package dk.grp1.tanks.weapon.DeadWeight.internal;

import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class DeadWeightActivator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        bundleContext.registerService(IGamePluginService.class.getName(), new DeadWeightPlugin(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}