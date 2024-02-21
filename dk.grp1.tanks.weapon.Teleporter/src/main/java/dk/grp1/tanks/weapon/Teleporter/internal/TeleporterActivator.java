package dk.grp1.tanks.weapon.Teleporter.internal;

import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class TeleporterActivator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println( "Starting dk.grp1.tanks.weapon.Teleporter" );
        bundleContext.registerService(IGamePluginService.class.getName(), new TeleporterPlugin(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println( "STOPPING dk.grp1.tanks.weapon.Teleporter" );
    }
}
