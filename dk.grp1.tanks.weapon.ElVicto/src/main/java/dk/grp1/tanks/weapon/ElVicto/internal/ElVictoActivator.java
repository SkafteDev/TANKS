package dk.grp1.tanks.weapon.ElVicto.internal;

import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class ElVictoActivator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println( "STARTING dk.grp1.tanks.weapon.ElVicto" );
        bundleContext.registerService(IGamePluginService.class.getName(), new ElVictoPlugin(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println( "STOPPING dk.grp1.tanks.weapon.ElVicto" );
    }
}
