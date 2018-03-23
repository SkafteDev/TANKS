package dk.grp1.tanks.SingleShot.internal;

import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class SingleShotActivator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        bundleContext.registerService(IGamePluginService.class.getName(), new SingleShotPlugin(), null);
        bundleContext.registerService(IWeapon.class.getName(), new SingleShotWeapon(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
