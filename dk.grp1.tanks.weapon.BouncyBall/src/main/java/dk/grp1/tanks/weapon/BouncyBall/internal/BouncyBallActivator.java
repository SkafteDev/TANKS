package dk.grp1.tanks.weapon.BouncyBall.internal;

import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class BouncyBallActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {

        bundleContext.registerService(IGamePluginService.class.getName(), new BouncyBallPlugin(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
